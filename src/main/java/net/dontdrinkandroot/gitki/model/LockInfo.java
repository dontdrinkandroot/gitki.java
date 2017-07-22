package net.dontdrinkandroot.gitki.model;

import java.time.Instant;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class LockInfo
{
    private FilePath path;

    private GitUser user;

    private Instant expiry;

    public LockInfo(FilePath path, GitUser user, Instant expiry)
    {
        this.path = path;
        this.user = user;
        this.expiry = expiry;
    }

    public FilePath getPath()
    {
        return this.path;
    }

    public GitUser getUser()
    {
        return this.user;
    }

    public Instant getExpiry()
    {
        return this.expiry;
    }

    public void setExpiry(Instant expiry)
    {
        this.expiry = expiry;
    }

    public boolean isExpired()
    {
        return this.expiry.isBefore(Instant.now());
    }
}
