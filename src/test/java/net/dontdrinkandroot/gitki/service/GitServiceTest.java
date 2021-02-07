package net.dontdrinkandroot.gitki.service;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.test.AbstractIntegrationTest;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class GitServiceTest extends AbstractIntegrationTest
{
    @Test
    public void testWorkflow() throws IOException, GitAPIException
    {
        DirectoryPath rootPath = new DirectoryPath();
        Assertions.assertEquals(
                rootPath,
                this.gitService.findExistingDirectoryPath(rootPath.appendDirectoryName("qwer"))
        );

        List<AbstractPath> directoryListing = this.gitService.listDirectory(rootPath);
        Assertions.assertEquals(0, directoryListing.size());

        FilePath testFilePath1 = rootPath.appendFileName("test.txt");
        this.gitService.addAndCommit(testFilePath1, "asdf", this.userCommitter, "Commit 1");
        Assertions.assertEquals("asdf", this.gitService.getContentAsString(testFilePath1));

        DirectoryPath testDirectoryPath1 = rootPath.appendDirectoryName("subpath");
        this.gitService.createDirectory(testDirectoryPath1);
        Assertions.assertTrue(this.gitService.exists(testDirectoryPath1));
        Assertions.assertEquals(
                testDirectoryPath1,
                this.gitService.findExistingDirectoryPath(testDirectoryPath1.appendDirectoryName("qwer")
                        .appendFileName("vfvfvf"))
        );

        FilePath testFilePath2 = testDirectoryPath1.appendFileName("test2.md");
        this.gitService.addAndCommit(testFilePath2, "yxcv", this.userAdmin, "Creating testFilePath2");
        Assertions.assertTrue(this.gitService.exists(testFilePath2));
        this.gitService.removeAndCommit(testFilePath2, this.userAdmin, "Removing testFilePath2");
        Assertions.assertFalse(this.gitService.exists(testFilePath2));
        Assertions.assertFalse(this.gitService.exists(testDirectoryPath1));

        Assertions.assertEquals(3, this.gitService.getRevisionCount());
    }

    @Test
    public void testListAllDirectories() throws IOException
    {
        List<DirectoryPath> directories;
        directories = this.gitService.listAllDirectories();
        Assertions.assertEquals(1, directories.size(), directories.toString());

        DirectoryPath fooPath = new DirectoryPath("foo");
        DirectoryPath barPath = new DirectoryPath("bar");
        DirectoryPath subPath = fooPath.appendDirectoryName("sub");

        this.gitService.createDirectory(fooPath);
        this.gitService.createDirectory(barPath);
        this.gitService.createDirectory(subPath);

        directories = this.gitService.listAllDirectories();
        Assertions.assertEquals(4, directories.size());
        Assertions.assertTrue(directories.contains(fooPath));
        Assertions.assertTrue(directories.contains(barPath));
        Assertions.assertTrue(directories.contains(subPath));
    }
}
