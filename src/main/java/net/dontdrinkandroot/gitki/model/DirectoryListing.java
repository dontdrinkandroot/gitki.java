package net.dontdrinkandroot.gitki.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryListing implements Serializable
{
    private List<Path> subDirectories;

    private List<Path> files;

    public DirectoryListing(List<Path> subDirectories, List<Path> files)
    {
        this.subDirectories = subDirectories;
        this.files = files;
    }

    public List<Path> getSubDirectories()
    {
        return this.subDirectories;
    }

    public List<Path> getFiles()
    {
        return this.files;
    }
}
