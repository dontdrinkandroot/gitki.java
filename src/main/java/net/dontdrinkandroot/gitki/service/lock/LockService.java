package net.dontdrinkandroot.gitki.service.lock;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.GitUser;
import net.dontdrinkandroot.gitki.model.LockInfo;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface LockService
{
    LockInfo lock(FilePath filePath, GitUser user) throws LockedException;

    void release(FilePath filePath, GitUser user) throws LockedException;

    /**
     * Forces the release of the Lock for the given FilePath.
     *
     * @param filePath The FilePath to unlock.
     */
    void forceRelease(FilePath filePath);

    LockInfo check(FilePath filePath);

    List<LockInfo> list();

    /**
     * Clears all locks.
     */
    void clear();
}
