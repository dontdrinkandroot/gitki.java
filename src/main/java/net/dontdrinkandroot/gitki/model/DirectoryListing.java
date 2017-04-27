package net.dontdrinkandroot.gitki.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryListing implements Serializable
{
    private List<DirectoryPath> subDirectories;

    private List<FilePath> files;

    public DirectoryListing(List<DirectoryPath> subDirectories, List<FilePath> files)
    {
        this.subDirectories = subDirectories;
        this.files = files;
    }

    public List<DirectoryPath> getSubDirectories()
    {
        return this.subDirectories;
    }

    public List<FilePath> getFiles()
    {
        return this.files;
    }
}
