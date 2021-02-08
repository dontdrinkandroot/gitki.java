package net.dontdrinkandroot.gitki.service.wiki

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.GitUser
import net.dontdrinkandroot.gitki.model.LockInfo
import net.dontdrinkandroot.gitki.service.lock.LockMissingException
import net.dontdrinkandroot.gitki.service.lock.LockedException
import org.eclipse.jgit.api.errors.GitAPIException
import java.io.IOException

interface WikiService {

    /**
     * Checks if there is an index file for the given DirectoryPath.
     *
     * @param directoryPath The [DirectoryPath] to check.
     * @return The index file or null if not found.
     */
    fun resolveIndexFile(directoryPath: DirectoryPath): FilePath?

    @Throws(LockedException::class)
    fun lock(filePath: FilePath, user: GitUser): LockInfo

    @Throws(LockedException::class, LockMissingException::class, IOException::class, GitAPIException::class)
    fun save(
        filePath: FilePath,
        user: GitUser,
        commitMessage: String,
        content: String
    )

    @Throws(LockedException::class, IOException::class, GitAPIException::class)
    fun saveAndUnlock(
        filePath: FilePath,
        user: GitUser,
        commitMessage: String,
        content: String
    )

    fun getLockInfo(filePath: FilePath): LockInfo

    /**
     * Unlocks the given [FilePath].
     *
     * @param filePath The [FilePath] to unlock.
     * @param user     The [GitUser] which wants to unlock.
     */
    @Throws(LockedException::class)
    fun unlock(filePath: FilePath, user: GitUser)
}