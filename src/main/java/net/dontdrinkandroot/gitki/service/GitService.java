package net.dontdrinkandroot.gitki.service;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import net.dontdrinkandroot.gitki.model.User;
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

    void add(Path path, byte[] content) throws IOException, GitAPIException;

    void addAndCommit(Path path, String content, User user, String commitMessage) throws IOException, GitAPIException;

    void createDirectory(Path path) throws IOException;

    void removeAndCommit(Path path, User user, String commitMessage) throws IOException, GitAPIException;

    void commit(User user, String commitMessage) throws GitAPIException;

    Path resolve(Path path);
}
