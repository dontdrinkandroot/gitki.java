package net.dontdrinkandroot.gitki.service;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface GitService
{
    DirectoryListing listDirectory(Path path) throws IOException;

    byte[] getContent(Path path) throws IOException;

    String getContentAsString(Path path) throws IOException;

    void save(Path path, String content, String commitMessage) throws IOException, GitAPIException;

    void createDirectory(Path path) throws IOException;
}
