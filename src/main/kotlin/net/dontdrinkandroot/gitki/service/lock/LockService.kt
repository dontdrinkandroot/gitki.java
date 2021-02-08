package net.dontdrinkandroot.gitki.service.lock

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.GitUser
import net.dontdrinkandroot.gitki.model.LockInfo

interface LockService {

    @Throws(LockedException::class)
    fun lock(filePath: FilePath, user: GitUser): LockInfo

    @Throws(LockedException::class)
    fun release(filePath: FilePath, user: GitUser)

    /**
     * Forces the release of the Lock for the given FilePath.
     *
     * @param filePath The FilePath to unlock.
     */
    fun forceRelease(filePath: FilePath)

    fun check(filePath: FilePath): LockInfo?

    fun list(): List<LockInfo>

    /**
     * Clears all locks.
     */
    fun clear()
}