package net.dontdrinkandroot.gitki.service.lock;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.GitUser;
import net.dontdrinkandroot.gitki.model.LockInfo;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface LockService
{
    LockInfo lock(FilePath filePath, GitUser user) throws LockedException;

    void release(FilePath filePath, GitUser user) throws LockedException;

    LockInfo check(FilePath filePath);
}
