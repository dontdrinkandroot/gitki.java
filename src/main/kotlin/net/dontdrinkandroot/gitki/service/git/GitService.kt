package net.dontdrinkandroot.gitki.service.git

import net.dontdrinkandroot.gitki.model.*
import org.eclipse.jgit.api.PullResult
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.transport.PushResult
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

interface GitService {

    val repositoryPath: Path

    @Throws(IOException::class)
    fun listDirectory(directoryPath: DirectoryPath): List<GitkiPath>

    @Throws(IOException::class)
    fun listAllDirectories(): List<DirectoryPath>

    fun exists(path: GitkiPath): Boolean

    fun findExistingDirectoryPath(path: GitkiPath): DirectoryPath

    @Throws(IOException::class)
    fun getContent(filePath: FilePath): ByteArray

    @Throws(IOException::class)
    fun getContentAsString(filePath: FilePath): String

    @Throws(IOException::class, GitAPIException::class)
    fun add(filePath: FilePath, content: ByteArray)

    @Throws(IOException::class, GitAPIException::class)
    fun addAndCommit(filePath: FilePath, content: String, user: GitUser, commitMessage: String)

    @Throws(IOException::class)
    fun createDirectory(directoryPath: DirectoryPath)

    @Throws(IOException::class, GitAPIException::class)
    fun removeAndCommit(filePath: FilePath, user: User, commitMessage: String)

    @Throws(GitAPIException::class)
    fun commit(user: GitUser, commitMessage: String)

    fun resolveAbsolutePath(path: GitkiPath): Path

    @Throws(FileNotFoundException::class)
    fun resolveAbsolutePath(path: GitkiPath, mustExist: Boolean): Path

    @Throws(IOException::class)
    fun getBasicFileAttributes(path: GitkiPath): BasicFileAttributes

    @get:Throws(GitAPIException::class)
    val revisionCount: Long

    @Throws(GitAPIException::class)
    fun getRevisionIterator(first: Long, count: Long): Iterator<RevCommit>

    @Throws(IOException::class, GitAPIException::class)
    fun moveAndCommit(sourcePath: GitkiPath, targetPath: GitkiPath, user: User, commitMessage: String)

    @Throws(GitAPIException::class)
    fun pull(): PullResult

    @Throws(GitAPIException::class)
    fun push(): Iterable<PushResult>
}