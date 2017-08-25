package net.dontdrinkandroot.gitki.service.wiki;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.GitUser;
import net.dontdrinkandroot.gitki.model.LockInfo;
import net.dontdrinkandroot.gitki.service.lock.LockMissingException;
import net.dontdrinkandroot.gitki.service.lock.LockedException;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface WikiService
{
    /**
     * Checks if there is an index file for the given DirectoryPath.
     *
     * @param directoryPath The {@link DirectoryPath} to check.
     * @return The index file or null if not found.
     */
    FilePath resolveIndexFile(DirectoryPath directoryPath);

    LockInfo lock(FilePath filePath, GitUser user) throws LockedException;

    void save(
            FilePath filePath,
            GitUser user,
            String commitMessage,
            String content
    ) throws LockedException, LockMissingException, IOException, GitAPIException;

    void saveAndUnlock(
            FilePath filePath,
            GitUser user,
            String commitMessage,
            String content
    ) throws LockedException, IOException, GitAPIException;

    LockInfo getLockInfo(FilePath filePath);

    /**
     * Unlocks the given {@link FilePath}.
     *
     * @param filePath The {@link FilePath} to unlock.
     * @param user     The {@link GitUser} which wants to unlock.
     */
    void unlock(FilePath filePath, GitUser user) throws LockedException;
}
