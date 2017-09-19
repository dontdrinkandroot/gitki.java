package net.dontdrinkandroot.gitki.service.git;

import net.dontdrinkandroot.gitki.model.*;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;

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

    List<AbstractPath> listDirectory(DirectoryPath directoryPath) throws IOException;

    List<DirectoryPath> listAllDirectories() throws IOException;

    boolean exists(AbstractPath path);

    DirectoryPath findExistingDirectoryPath(AbstractPath path);

    byte[] getContent(FilePath filePath) throws IOException;

    String getContentAsString(FilePath filePath) throws IOException;

    void add(FilePath filePath, byte[] content) throws IOException, GitAPIException;

    void addAndCommit(
            FilePath filePath,
            String content,
            GitUser user,
            String commitMessage
    ) throws IOException, GitAPIException;

    void createDirectory(DirectoryPath directoryPath) throws IOException;

    void removeAndCommit(FilePath filePath, User user, String commitMessage) throws IOException, GitAPIException;

    void commit(GitUser user, String commitMessage) throws GitAPIException;

    Path resolveAbsolutePath(AbstractPath path);

    Path resolveAbsolutePath(AbstractPath path, boolean mustExist) throws FileNotFoundException;

    BasicFileAttributes getBasicFileAttributes(AbstractPath path) throws IOException;

    long getRevisionCount() throws GitAPIException;

    Iterator<? extends RevCommit> getRevisionIterator(long first, long count) throws GitAPIException;

    void moveAndCommit(
            AbstractPath sourcePath,
            AbstractPath targetPath,
            User user,
            String commitMessage
    ) throws IOException, GitAPIException;

    PullResult pull() throws GitAPIException;

    Iterable<PushResult> push() throws GitAPIException;
}
