package net.dontdrinkandroot.gitki.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class PathTest
{
    @Test
    public void testDirectoryPath()
    {
        DirectoryPath path;

        path = new DirectoryPath();
        Assert.assertTrue(path.isRoot());
        Assert.assertEquals("", path.toString());

        path = new DirectoryPath("asdf");
        Assert.assertFalse(path.isRoot());
        Assert.assertEquals("asdf/", path.toString());

        path = DirectoryPath.parse("asdf/qwer/");
        Assert.assertFalse(path.isRoot());
        Assert.assertEquals("asdf/qwer/", path.toString());

        path = path.getParent();
        Assert.assertEquals("asdf/", path.toString());

        path = path.getParent();
        Assert.assertEquals("", path.toString());

        path = new DirectoryPath("asdf");
        FilePath filePath = path.appendFileName("qwer");
        Assert.assertEquals("asdf/qwer", filePath.toString());

        path = new DirectoryPath("asdf");
        path = path.appendDirectoryName("qwer");
        Assert.assertEquals("asdf/qwer/", path.toString());
    }

    @Test
    public void testToPath()
    {
        FilePath filePath;
        DirectoryPath directoryPath;

        FilePath path = new FilePath("asdf/qwer");
        Assert.assertEquals("asdf/qwer", path.toPath().toString());

        directoryPath = new DirectoryPath("asdf/qwer/");
        Assert.assertEquals("asdf/qwer", path.toPath().toString());
    }

    @Test
    public void testFilePath()
    {
        FilePath filePath;
        DirectoryPath directoryPath;

        filePath = FilePath.parse("asdf");
        Assert.assertEquals("asdf", filePath.getName());
        Assert.assertEquals("asdf", filePath.toString());

        directoryPath = filePath.getParent();
        Assert.assertTrue(directoryPath.isRoot());

        filePath = FilePath.parse("asdf/qwer");
        Assert.assertEquals("qwer", filePath.getName());
        Assert.assertEquals("asdf/qwer", filePath.toString());

        directoryPath = filePath.getParent();
        Assert.assertEquals("asdf/", directoryPath.toString());
        Assert.assertFalse(directoryPath.isRoot());

        directoryPath = directoryPath.getParent();
        Assert.assertTrue(directoryPath.isRoot());
    }

    @Test
    public void testEquals()
    {
        Assert.assertTrue(new DirectoryPath().equals(new DirectoryPath()));
        Assert.assertTrue(DirectoryPath.parse("asdf/qwer/").equals(DirectoryPath.parse("asdf/qwer/")));
        Assert.assertTrue(FilePath.parse("asdf/qwer").equals(FilePath.parse("asdf/qwer")));
    }
}
