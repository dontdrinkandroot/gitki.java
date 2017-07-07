package net.dontdrinkandroot.gitki.service;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.test.AbstractIntegrationTest;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class GitServiceTest extends AbstractIntegrationTest
{
    @Before
    public void resetRepository() throws IOException
    {
        Path repositoryPath = this.gitService.getRepositoryPath();
        Files.walk(repositoryPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .peek(System.out::println)
                .forEach(File::delete);
        Repository repository = FileRepositoryBuilder.create(repositoryPath.resolve(".git").toFile());
        repository.create();
    }

    @Test
    public void testWorkflow() throws IOException, GitAPIException
    {
        DirectoryPath rootPath = new DirectoryPath();
        Assert.assertEquals(
                rootPath,
                this.gitService.findExistingDirectoryPath(rootPath.appendDirectoryName("qwer"))
        );

        List<AbstractPath> directoryListing = this.gitService.listDirectory(rootPath);
        Assert.assertEquals(0, directoryListing.size());

        FilePath testFilePath1 = rootPath.appendFileName("test.txt");
        this.gitService.addAndCommit(testFilePath1, "asdf", this.userCommitter, "Commit 1");
        Assert.assertEquals("asdf", this.gitService.getContentAsString(testFilePath1));

        DirectoryPath testDirectoryPath1 = rootPath.appendDirectoryName("subpath");
        this.gitService.createDirectory(testDirectoryPath1);
        Assert.assertTrue(this.gitService.exists(testDirectoryPath1));
        Assert.assertEquals(
                testDirectoryPath1,
                this.gitService.findExistingDirectoryPath(testDirectoryPath1.appendDirectoryName("qwer")
                        .appendFileName("vfvfvf"))
        );

        FilePath testFilePath2 = testDirectoryPath1.appendFileName("test2.md");
        this.gitService.addAndCommit(testFilePath2, "yxcv", this.userAdmin, "Creating testFilePath2");
        Assert.assertTrue(this.gitService.exists(testFilePath2));
        this.gitService.removeAndCommit(testFilePath2, this.userAdmin, "Removing testFilePath2");
        Assert.assertFalse(this.gitService.exists(testFilePath2));
        Assert.assertFalse(this.gitService.exists(testDirectoryPath1));

        Assert.assertEquals(3, this.gitService.getRevisionCount());
    }
}
