package net.dontdrinkandroot.gitki.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class AbstractPath implements Serializable
{
    public static final String SEPARATOR = "/";

    private String name;

    private DirectoryPath parent;

    public String getName()
    {
        return this.name;
    }

    protected void setName(String name)
    {
        if (null == name || "".equals(name.trim())) {
            throw new IllegalArgumentException("Name must not be empty");
        }

        this.name = name;
    }

    public DirectoryPath getParent()
    {
        return this.parent;
    }

    protected void setParent(DirectoryPath parent)
    {
        this.parent = parent;
    }

    public List<String> getSegmentNames()
    {
        if (this.isRoot()) {
            return new ArrayList<>();
        }

        List<String> segmentNames = this.getParent().getSegmentNames();
        segmentNames.add(this.getName());

        return segmentNames;
    }

    public abstract Path toPath();

    public abstract boolean isRoot();
}
