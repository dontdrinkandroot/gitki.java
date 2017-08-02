package net.dontdrinkandroot.gitki.service.lock;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.GitUser;
import net.dontdrinkandroot.gitki.model.LockInfo;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
public class MapLockService implements LockService
{
    private Map<FilePath, LockInfo> lockMap = new HashMap<>();

    private Duration lockDuration = Duration.ofMinutes(5);

    @Override
    public LockInfo lock(FilePath filePath, GitUser user) throws LockedException
    {
        LockInfo lockInfo = this.check(filePath);
        if (null == lockInfo) {
            lockInfo = new LockInfo(filePath, user, this.computeExpiry());
            this.lockMap.put(filePath, lockInfo);
            return lockInfo;
        }

        if (user.equals(lockInfo.getUser())) {
            lockInfo.setExpiry(this.computeExpiry());
            return lockInfo;
        }

        throw new LockedException(lockInfo);
    }

    @Override
    public void release(FilePath filePath, GitUser user) throws LockedException
    {
        LockInfo lockInfo = this.check(filePath);
        if (null == lockInfo) {
            return;
        }

        if (user.equals(lockInfo.getUser())) {
            this.lockMap.remove(filePath);
        }

        throw new LockedException(lockInfo);
    }

    @Override
    public LockInfo check(FilePath filePath)
    {
        LockInfo lockInfo = this.lockMap.get(filePath);
        if (null == lockInfo) {
            return null;
        }

        if (lockInfo.isExpired()) {
            this.lockMap.remove(filePath);
            return null;
        }

        return lockInfo;
    }

    @Override
    public List<LockInfo> list()
    {
        return this.lockMap.values()
                .stream()
                .filter(lockInfo -> !lockInfo.isExpired())
                .sorted()
                .collect(Collectors.toList());
    }

    private Instant computeExpiry()
    {
        return Instant.now().plus(this.lockDuration);
    }

    public Duration getLockDuration()
    {
        return this.lockDuration;
    }

    public void setLockDuration(Duration lockDuration)
    {
        this.lockDuration = lockDuration;
    }
}
