package net.dontdrinkandroot.gitki.git

import org.apache.commons.collections4.IterableUtils
import org.apache.commons.io.FileUtils
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.IOException

class GitTest {

    @BeforeEach
    @Throws(IOException::class)
    fun before() {
        FileUtils.deleteDirectory(File(REPO_PATH))
    }

    @Test
    @Throws(IOException::class, GitAPIException::class)
    fun testCommit() {
        val repository = FileRepositoryBuilder.create(File(REPO_PATH + ".git"))
        repository.create()
        val git = Git(repository)
        FileUtils.write(File(REPO_PATH + "test.txt"), "Test content", "utf-8")
        git.add().addFilepattern("test.txt")
        git.commit().setMessage("Test message").setAuthor("Test Author", "test@example.com").call()
        val commits = IterableUtils.toList(git.log().call())
        Assertions.assertEquals(1, commits.size)
        val revCommit = commits[0]
        Assertions.assertEquals("Test message", revCommit.fullMessage)
        val author = revCommit.authorIdent
        Assertions.assertEquals("Test Author", author.name)
        Assertions.assertEquals("test@example.com", author.emailAddress)
    }

    companion object {

        const val REPO_PATH = "/tmp/gitki_test_repo/"
    }
}