package net.dontdrinkandroot.gitki.service.lock;

import net.dontdrinkandroot.gitki.model.LockInfo;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class LockedException extends Exception
{
    private final LockInfo lockInfo;

    public LockedException(LockInfo lockInfo)
    {
        this.lockInfo = lockInfo;
    }

    public LockInfo getLockInfo()
    {
        return this.lockInfo;
    }
}
