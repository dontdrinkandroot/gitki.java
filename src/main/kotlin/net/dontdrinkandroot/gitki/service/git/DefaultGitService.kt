package net.dontdrinkandroot.gitki.service.git

import net.dontdrinkandroot.gitki.config.GitkiConfigurationProperties
import net.dontdrinkandroot.gitki.model.*
import org.apache.commons.lang3.StringUtils
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.PullResult
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.api.errors.NoHeadException
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.PushResult
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import javax.inject.Inject

@Service
class DefaultGitService @Inject constructor(configurationProperties: GitkiConfigurationProperties) : GitService {

    override val repositoryPath: Path

    private val gitPath = Paths.get(".git")

    private val git: Git

    init {
        if (!StringUtils.endsWith(configurationProperties.git.repository, File.separator)) {
            throw RuntimeException("Git Dir must end with " + File.separator)
        }
        val gitPath = Paths.get(configurationProperties.git.repository)
        if (!gitPath.isAbsolute) {
            throw RuntimeException("Repository path must be absolute")
        }
        repositoryPath = gitPath
        val repository = FileRepositoryBuilder.create(repositoryPath.resolve(".git").toFile())
        if (repository.findRef("HEAD") == null) {
            repository.create()
        }
        git = Git(repository)
    }

    @Throws(IOException::class)
    override fun listDirectory(directoryPath: DirectoryPath): List<GitkiPath> {
        val absolutePath = this.resolveAbsolutePath(directoryPath, true)
        val directoryStream = Files.newDirectoryStream(absolutePath)
        val entries: MutableList<GitkiPath> = ArrayList()
        for (subPath in directoryStream) {
            val relativeSubPath = repositoryPath.relativize(subPath)
            if (!relativeSubPath.startsWith(".git")) {
                if (Files.isDirectory(subPath)) {
                    entries.add(DirectoryPath.from(relativeSubPath))
                } else {
                    entries.add(FilePath.from(relativeSubPath))
                }
            }
        }
        entries.sortWith { obj: GitkiPath, other: GitkiPath -> obj.compareTo(other) }
        return entries
    }

    @Throws(IOException::class)
    override fun listAllDirectories(): List<DirectoryPath> {
        val directories: MutableList<DirectoryPath> = ArrayList()
        Files.walkFileTree(
            repositoryPath, object : AbstractDirectoryVisitor() {
                override fun visitDirectory(dir: Path, attrs: BasicFileAttributes) {
                    val relativePath = repositoryPath.relativize(dir)
                    if (isValid(relativePath)) {
                        directories.add(DirectoryPath.from(relativePath))
                    }
                }
            })
        return directories
    }

    override fun exists(path: GitkiPath): Boolean {
        val absolutePath = this.resolveAbsolutePath(path)
        return if (path.directoryPath) {
            Files.exists(absolutePath) && Files.isDirectory(absolutePath)
        } else {
            Files.exists(absolutePath) && Files.isRegularFile(absolutePath)
        }
    }

    override fun findExistingDirectoryPath(path: GitkiPath): DirectoryPath {
        var directoryPath: DirectoryPath = if (path.isFilePath) path.parent!! else path as DirectoryPath
        do {
            if (exists(directoryPath)) return directoryPath
            directoryPath = directoryPath.parent!!
        } while (!directoryPath.root)
        return DirectoryPath()
    }

    @Throws(IOException::class)
    override fun getContent(filePath: FilePath): ByteArray {
        val fullPath = this.resolveAbsolutePath(filePath, true)
        return Files.readAllBytes(fullPath)
    }

    @Throws(IOException::class)
    override fun getContentAsString(filePath: FilePath): String {
        val bytes = getContent(filePath)
        return String(bytes)
    }

    @Throws(IOException::class, GitAPIException::class)
    override fun addAndCommit(
        filePath: FilePath,
        content: String,
        user: GitUser,
        commitMessage: String
    ) {
        add(filePath, content.toByteArray())
        commit(user, commitMessage)
    }

    @Throws(IOException::class, GitAPIException::class)
    override fun add(filePath: FilePath, content: ByteArray) {
        createDirectory(filePath.parent!!)
        val absolutePath = this.resolveAbsolutePath(filePath)
        Files.write(absolutePath, content)
        git.add().addFilepattern(filePath.toString()).call()
    }

    @Throws(IOException::class, GitAPIException::class)
    override fun moveAndCommit(
        sourcePath: GitkiPath,
        targetPath: GitkiPath,
        user: User,
        commitMessage: String
    ) {
        val absoluteSource = this.resolveAbsolutePath(sourcePath)
        val absoluteTarget = this.resolveAbsolutePath(targetPath)
        Files.move(absoluteSource, absoluteTarget, StandardCopyOption.ATOMIC_MOVE)
        git.add().addFilepattern(targetPath.toString()).call()
        git.rm().addFilepattern(sourcePath.toString()).call()
        commit(user, commitMessage)
    }

    @Throws(GitAPIException::class)
    override fun pull(): PullResult {
        return git.pull().call()
    }

    @Throws(GitAPIException::class)
    override fun push(): Iterable<PushResult> {
        return git.push().call()
    }

    @Throws(IOException::class)
    override fun createDirectory(directoryPath: DirectoryPath) {
        val fullPath = this.resolveAbsolutePath(directoryPath)
        Files.createDirectories(fullPath)
    }

    @Throws(IOException::class, GitAPIException::class)
    override fun removeAndCommit(filePath: FilePath, user: User, commitMessage: String) {
        git.rm().addFilepattern(filePath.toString()).call()
        commit(user, commitMessage)
    }

    @Throws(GitAPIException::class)
    override fun commit(user: GitUser, commitMessage: String) {
        git.commit().setMessage(commitMessage).setAuthor(user.fullName, user.email).call()
    }

    override fun resolveAbsolutePath(path: GitkiPath): Path {
        val relativePath = path.nioPath
        if (relativePath.startsWith(".git")) {
            throw RuntimeException("Cannot access .git directory")
        }
        val absolutePath = repositoryPath.resolve(relativePath)
        if (!absolutePath.startsWith(repositoryPath)) {
            throw RuntimeException("Trying to access path outside of repository")
        }
        return absolutePath
    }

    @Throws(FileNotFoundException::class)
    override fun resolveAbsolutePath(path: GitkiPath, mustExist: Boolean): Path {
        val absolutePath = this.resolveAbsolutePath(path)
        if (mustExist && !Files.exists(absolutePath)) {
            throw FileNotFoundException(String.format("%s does not exist", absolutePath.toString()))
        }
        return absolutePath
    }

    @Throws(IOException::class)
    override fun getBasicFileAttributes(path: GitkiPath): BasicFileAttributes {
        val absolutePath = this.resolveAbsolutePath(path, true)
        return Files.readAttributes(absolutePath, BasicFileAttributes::class.java)
    }

    /* No HEAD exists and therefore no history */
    @get:Throws(GitAPIException::class)
    override val revisionCount: Long
        get() = try {
            val commits = git.log().call()
            var count: Long = 0
            for (commit in commits) {
                count++
            }
            count
        } catch (e: NoHeadException) {
            /* No HEAD exists and therefore no history */
            0
        }

    @Throws(GitAPIException::class)
    override fun getRevisionIterator(first: Long, count: Long): Iterator<RevCommit> {
        return git.log().setSkip(first.toInt()).setMaxCount(count.toInt()).call().iterator()
    }

    private fun isValid(relativePath: Path): Boolean {
        return !relativePath.startsWith(gitPath)
    }
}