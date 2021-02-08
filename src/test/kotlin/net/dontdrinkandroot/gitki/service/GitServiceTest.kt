package net.dontdrinkandroot.gitki.service

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.test.AbstractIntegrationTest
import org.eclipse.jgit.api.errors.GitAPIException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.io.IOException

class GitServiceTest : AbstractIntegrationTest() {

    @Test
    @Throws(IOException::class, GitAPIException::class)
    fun testWorkflow() {
        val rootPath = DirectoryPath()
        Assertions.assertEquals(
            rootPath,
            gitService.findExistingDirectoryPath(rootPath.appendDirectoryName("qwer"))
        )
        val directoryListing = gitService.listDirectory(rootPath)
        Assertions.assertEquals(0, directoryListing.size)
        val testFilePath1 = rootPath.appendFileName("test.txt")
        gitService.addAndCommit(testFilePath1, "asdf", userCommitter, "Commit 1")
        Assertions.assertEquals("asdf", gitService.getContentAsString(testFilePath1))
        val testDirectoryPath1 = rootPath.appendDirectoryName("subpath")
        gitService.createDirectory(testDirectoryPath1)
        Assertions.assertTrue(gitService.exists(testDirectoryPath1))
        Assertions.assertEquals(
            testDirectoryPath1,
            gitService.findExistingDirectoryPath(
                testDirectoryPath1.appendDirectoryName("qwer")
                    .appendFileName("vfvfvf")
            )
        )
        val testFilePath2 = testDirectoryPath1.appendFileName("test2.md")
        gitService.addAndCommit(testFilePath2, "yxcv", userAdmin, "Creating testFilePath2")
        Assertions.assertTrue(gitService.exists(testFilePath2))
        gitService.removeAndCommit(testFilePath2, userAdmin, "Removing testFilePath2")
        Assertions.assertFalse(gitService.exists(testFilePath2))
        Assertions.assertFalse(gitService.exists(testDirectoryPath1))
        Assertions.assertEquals(3, gitService.revisionCount)
    }

    @Test
    @Throws(IOException::class)
    fun testListAllDirectories() {
        var directories: List<DirectoryPath?>
        directories = gitService.listAllDirectories()
        Assertions.assertEquals(1, directories.size, directories.toString())
        val fooPath = DirectoryPath("foo")
        val barPath = DirectoryPath("bar")
        val subPath = fooPath.appendDirectoryName("sub")
        gitService.createDirectory(fooPath)
        gitService.createDirectory(barPath)
        gitService.createDirectory(subPath)
        directories = gitService.listAllDirectories()
        Assertions.assertEquals(4, directories.size)
        Assertions.assertTrue(directories.contains(fooPath))
        Assertions.assertTrue(directories.contains(barPath))
        Assertions.assertTrue(directories.contains(subPath))
    }
}