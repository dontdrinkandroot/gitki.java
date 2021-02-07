package net.dontdrinkandroot.gitki.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class PathTest
{
    @Test
    public void testDirectoryPath() {
        DirectoryPath path;

        path = new DirectoryPath();
        Assertions.assertTrue(path.isRoot());
        Assertions.assertEquals("", path.toString());

        path = new DirectoryPath("asdf");
        Assertions.assertFalse(path.isRoot());
        Assertions.assertEquals("asdf/", path.toString());

        path = DirectoryPath.parse("asdf/qwer/");
        Assertions.assertFalse(path.isRoot());
        Assertions.assertEquals("asdf/qwer/", path.toString());

        path = path.getParent();
        Assertions.assertEquals("asdf/", path.toString());

        path = path.getParent();
        Assertions.assertEquals("", path.toString());

        path = new DirectoryPath("asdf");
        FilePath filePath = path.appendFileName("qwer");
        Assertions.assertEquals("asdf/qwer", filePath.toString());

        path = new DirectoryPath("asdf");
        path = path.appendDirectoryName("qwer");
        Assertions.assertEquals("asdf/qwer/", path.toString());
    }

    @Test
    public void testToPath()
    {
        FilePath filePath;
        DirectoryPath directoryPath;

        FilePath path = new FilePath("asdf/qwer");
        Assertions.assertEquals("asdf/qwer", path.toPath().toString());

        directoryPath = new DirectoryPath("asdf/qwer/");
        Assertions.assertEquals("asdf/qwer", path.toPath().toString());
    }

    @Test
    public void testFilePath() {
        FilePath filePath;
        DirectoryPath directoryPath;

        filePath = FilePath.parse("asdf");
        Assertions.assertEquals("asdf", filePath.getName());
        Assertions.assertEquals("asdf", filePath.toString());

        directoryPath = filePath.getParent();
        Assertions.assertTrue(directoryPath.isRoot());

        filePath = FilePath.parse("asdf/qwer");
        Assertions.assertEquals("qwer", filePath.getName());
        Assertions.assertEquals("asdf/qwer", filePath.toString());

        directoryPath = filePath.getParent();
        Assertions.assertEquals("asdf/", directoryPath.toString());
        Assertions.assertFalse(directoryPath.isRoot());

        directoryPath = directoryPath.getParent();
        Assertions.assertTrue(directoryPath.isRoot());
    }

    @Test
    public void testEquals() {
        Assertions.assertTrue(new DirectoryPath().equals(new DirectoryPath()));
        Assertions.assertTrue(DirectoryPath.parse("asdf/qwer/").equals(DirectoryPath.parse("asdf/qwer/")));
        Assertions.assertTrue(FilePath.parse("asdf/qwer").equals(FilePath.parse("asdf/qwer")));
    }
}
