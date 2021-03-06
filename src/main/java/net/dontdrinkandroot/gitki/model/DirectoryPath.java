package net.dontdrinkandroot.gitki.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryPath extends AbstractPath
{
    private DirectoryPath(String name, DirectoryPath directoryPath)
    {
        this.setName(name);
        this.setParent(directoryPath);
    }

    public DirectoryPath()
    {
    }

    public DirectoryPath(String name)
    {
        this.setName(name);
        this.setParent(new DirectoryPath());
    }

    @Override
    public boolean isRoot()
    {
        return null == this.getParent();
    }

    public static DirectoryPath parse(String pathString)
    {
        if (pathString.startsWith(SEPARATOR)) {
            throw new IllegalArgumentException("Path string must not end with " + SEPARATOR);
        }

        String[] parts = pathString.split(SEPARATOR);

        return DirectoryPath.from(parts);
    }

    public static DirectoryPath from(String[] parts)
    {
        DirectoryPath path = new DirectoryPath(parts[parts.length - 1]);

        if (parts.length > 1) {
            path.setParent(DirectoryPath.from(Arrays.copyOfRange(parts, 0, parts.length - 1)));
        }

        return path;
    }

    public FilePath appendFileName(String name)
    {
        return new FilePath(name, this);
    }

    public DirectoryPath appendDirectoryName(String name)
    {
        return new DirectoryPath(name, this);
    }

    @Override
    public boolean isDirectoryPath()
    {
        return true;
    }

    @Override
    public String toString()
    {
        if (null == this.getParent()) {
            return "";
        }

        return this.getParent().toString() + this.getName() + "/";
    }

    @Override
    public Path toPath()
    {
        if (this.isRoot()) {
            return Paths.get("");
        }

        Path parentPath = this.getParent().toPath();
        return parentPath.resolve(this.getName());
    }

    public static DirectoryPath from(Path path)
    {
        if ("".equals(path.toString())) {
            return new DirectoryPath();
        }

        Path parent = path.getParent();
        if (null != parent) {
            return DirectoryPath.from(parent).appendDirectoryName(path.getFileName().toString());
        }

        return new DirectoryPath(path.getFileName().toString());
    }
}
