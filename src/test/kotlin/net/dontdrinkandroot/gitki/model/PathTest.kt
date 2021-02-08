package net.dontdrinkandroot.gitki.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PathTest {

    @Test
    fun testDirectoryPath() {

        var path: DirectoryPath?

        path = DirectoryPath()
        Assertions.assertTrue(path.root)
        Assertions.assertEquals("", path.toString())

        path = DirectoryPath("asdf")
        Assertions.assertFalse(path.root)
        Assertions.assertEquals("asdf/", path.toString())

        path = DirectoryPath.parse("asdf/qwer/")
        Assertions.assertFalse(path.root)
        Assertions.assertEquals("asdf/qwer/", path.toString())

        path = path.parent
        Assertions.assertEquals("asdf/", path.toString())

        path = path!!.parent
        Assertions.assertEquals("", path.toString())

        path = DirectoryPath("asdf")
        val filePath = path.appendFileName("qwer")
        Assertions.assertEquals("asdf/qwer", filePath.toString())

        path = DirectoryPath("asdf")
        path = path.appendDirectoryName("qwer")
        Assertions.assertEquals("asdf/qwer/", path.toString())
    }

    @Test
    fun testToPath() {
        val filePath = FilePath("asdf/qwer")
        Assertions.assertEquals("asdf/qwer", filePath.nioPath.toString())

        val directoryPath = DirectoryPath("asdf/qwer/")
        Assertions.assertEquals("asdf/qwer", directoryPath.nioPath.toString())
    }

    @Test
    fun testFilePath() {
        var directoryPath: DirectoryPath?
        var filePath: FilePath = FilePath.parse("asdf")
        Assertions.assertEquals("asdf", filePath.name)
        Assertions.assertEquals("asdf", filePath.toString())

        directoryPath = filePath.parent
        Assertions.assertTrue(directoryPath!!.root)

        filePath = FilePath.parse("asdf/qwer")
        Assertions.assertEquals("qwer", filePath.name)
        Assertions.assertEquals("asdf/qwer", filePath.toString())

        directoryPath = filePath.parent
        Assertions.assertEquals("asdf/", directoryPath.toString())
        Assertions.assertFalse(directoryPath!!.root)

        directoryPath = directoryPath.parent
        Assertions.assertTrue(directoryPath!!.root)
    }

    @Test
    fun testEquals() {
        Assertions.assertTrue(DirectoryPath() == DirectoryPath())
        Assertions.assertTrue(DirectoryPath.parse("asdf/qwer/") == DirectoryPath.parse("asdf/qwer/"))
        Assertions.assertTrue(FilePath.parse("asdf/qwer") == FilePath.parse("asdf/qwer"))
    }
}