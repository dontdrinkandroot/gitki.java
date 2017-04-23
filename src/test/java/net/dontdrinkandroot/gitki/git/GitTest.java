package net.dontdrinkandroot.gitki.git;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class GitTest
{
    public static final String REPO_PATH = "/tmp/gitki_test_repo/";

    @Before
    public void before() throws IOException
    {
        FileUtils.deleteDirectory(new File(REPO_PATH));
    }

    @Test
    public void testCommit() throws IOException, GitAPIException
    {
        Repository repository = FileRepositoryBuilder.create(new File(REPO_PATH + ".git"));
        repository.create();

        Git git = new Git(repository);

        FileUtils.write(new File(REPO_PATH + "test.txt"), "Test content", "utf-8");

        git.add().addFilepattern("test.txt");
        git.commit().setMessage("Test message").setAuthor("Test Author", "test@example.com").call();

        List<RevCommit> commits = IterableUtils.toList(git.log().call());
        Assert.assertEquals(1, commits.size());

        RevCommit revCommit = commits.get(0);
        Assert.assertEquals("Test message", revCommit.getFullMessage());
        PersonIdent author = revCommit.getAuthorIdent();
        Assert.assertEquals("Test Author", author.getName());
        Assert.assertEquals("test@example.com", author.getEmailAddress());
    }
}
