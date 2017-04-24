package net.dontdrinkandroot.gitki.service;

import net.dontdrinkandroot.gitki.model.DirectoryListing;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface GitService
{
    DirectoryListing listDirectory(Path path) throws IOException;
}
