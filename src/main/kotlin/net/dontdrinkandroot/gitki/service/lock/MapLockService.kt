package net.dontdrinkandroot.gitki.service.lock

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.GitUser
import net.dontdrinkandroot.gitki.model.LockInfo
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.stream.Collectors

@Service
class MapLockService : LockService {

    private val lockMap: MutableMap<FilePath, LockInfo> = HashMap()

    private val lockDuration: Duration = Duration.ofMinutes(5)

    @Throws(LockedException::class)
    override fun lock(filePath: FilePath, user: GitUser): LockInfo {
        var lockInfo = this.check(filePath)
        if (null == lockInfo) {
            lockInfo = LockInfo(filePath, user, computeExpiry())
            lockMap[filePath] = lockInfo
            return lockInfo
        }
        if (user == lockInfo.user) {
            lockInfo.expiry = computeExpiry()
            return lockInfo
        }
        throw LockedException(lockInfo)
    }

    @Throws(LockedException::class)
    override fun release(filePath: FilePath, user: GitUser) {
        val lockInfo = this.check(filePath) ?: return
        if (user == lockInfo.user) {
            lockMap.remove(filePath)
            return
        }
        throw LockedException(lockInfo)
    }

    override fun forceRelease(filePath: FilePath) {
        lockMap.remove(filePath)
    }

    override fun check(filePath: FilePath): LockInfo? {
        val lockInfo = lockMap[filePath] ?: return null
        if (lockInfo.expired) {
            lockMap.remove(filePath)
            return null
        }
        return lockInfo
    }

    override fun list(): List<LockInfo> {
        return lockMap.values
            .stream()
            .filter { lockInfo: LockInfo -> !lockInfo.expired }
            .sorted()
            .collect(Collectors.toList())
    }

    override fun clear() {
        lockMap.clear()
    }

    private fun computeExpiry(): Instant = Instant.now().plus(lockDuration)
}