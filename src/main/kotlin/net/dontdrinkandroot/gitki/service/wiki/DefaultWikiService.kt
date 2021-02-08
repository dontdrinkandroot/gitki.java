package net.dontdrinkandroot.gitki.service.wiki

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.GitUser
import net.dontdrinkandroot.gitki.model.LockInfo
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.service.lock.LockMissingException
import net.dontdrinkandroot.gitki.service.lock.LockService
import net.dontdrinkandroot.gitki.service.lock.LockedException
import org.eclipse.jgit.api.errors.GitAPIException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.*
import javax.inject.Inject

@Service
class DefaultWikiService @Inject constructor(private val gitService: GitService, private val lockService: LockService) :
    WikiService {

    private var indexFiles: List<String> = ArrayList()

    @Value("#{gitkiConfigurationProperties.indexFiles}")
    fun setIndexFiles(indexFiles: List<String>) {
        this.indexFiles = indexFiles
    }

    override fun resolveIndexFile(directoryPath: DirectoryPath): FilePath? {
        for (indexFile in indexFiles) {
            val indexFilePath = directoryPath.appendFileName(indexFile)
            if (gitService.exists(indexFilePath)) {
                return indexFilePath
            }
        }
        return null
    }

    @Throws(LockedException::class)
    override fun lock(filePath: FilePath, user: GitUser): LockInfo = lockService.lock(filePath, user)

    @Throws(LockedException::class, LockMissingException::class, IOException::class, GitAPIException::class)
    override fun save(
        filePath: FilePath,
        user: GitUser,
        commitMessage: String,
        content: String
    ) {
        lockService.lock(filePath, user)
        gitService.addAndCommit(filePath, content, user, commitMessage)
    }

    @Throws(LockedException::class, IOException::class, GitAPIException::class)
    override fun saveAndUnlock(
        filePath: FilePath,
        user: GitUser,
        commitMessage: String,
        content: String
    ) {
        lockService.lock(filePath, user)
        gitService.addAndCommit(filePath, content, user, commitMessage)
        lockService.release(filePath, user)
    }

    override fun getLockInfo(filePath: FilePath): LockInfo = lockService.check(filePath)!!

    @Throws(LockedException::class)
    override fun unlock(filePath: FilePath, user: GitUser) = lockService.release(filePath, user)
}