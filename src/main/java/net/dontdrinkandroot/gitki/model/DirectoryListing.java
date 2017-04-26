package net.dontdrinkandroot.gitki.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryListing implements Serializable
{
    private List<DirectoryPath> subDirectories;

    private List<Path> files;

    public DirectoryListing(List<DirectoryPath> subDirectories, List<Path> files)
    {
        this.subDirectories = subDirectories;
        this.files = files;
    }

    public List<DirectoryPath> getSubDirectories()
    {
        return this.subDirectories;
    }

    public List<Path> getFiles()
    {
        return this.files;
    }
}
