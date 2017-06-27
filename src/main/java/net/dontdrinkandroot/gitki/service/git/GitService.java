package net.dontdrinkandroot.gitki.service.git;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.gitki.model.User;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface GitService
{
    Path getRepositoryPath();

    List<AbstractPath> listDirectory(Path path) throws IOException;

    byte[] getContent(Path path) throws IOException;

    String getContentAsString(Path path) throws IOException;

    void add(Path path, byte[] content) throws IOException, GitAPIException;

    void addAndCommit(Path path, String content, User user, String commitMessage) throws IOException, GitAPIException;

    void createDirectory(Path path) throws IOException;

    void removeAndCommit(Path path, User user, String commitMessage) throws IOException, GitAPIException;

    void commit(User user, String commitMessage) throws GitAPIException;

    Path resolve(Path path) throws FileNotFoundException;

    Path resolve(Path path, boolean mustExist) throws FileNotFoundException;

    BasicFileAttributes getBasicFileAttributes(Path path) throws IOException;

    long getRevisionCount() throws GitAPIException;

    Iterator<? extends RevCommit> getRevisionIterator(long first, long count) throws GitAPIException;
}
