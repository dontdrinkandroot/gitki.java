package net.dontdrinkandroot.gitki.model;

import java.io.Serializable;
import java.nio.file.Path;

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

    public abstract Path toPath();
}
