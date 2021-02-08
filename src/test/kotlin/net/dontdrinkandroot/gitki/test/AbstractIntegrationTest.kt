package net.dontdrinkandroot.gitki.test

import net.dontdrinkandroot.gitki.Application
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.service.configuration.DefaultConfigurationService
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.service.lock.LockService
import net.dontdrinkandroot.gitki.service.user.UserService
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.junit.jupiter.api.BeforeEach
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.test.context.ActiveProfiles
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

@ActiveProfiles("test")
@SpringBootTest(classes = [Application::class])
abstract class AbstractIntegrationTest {

    protected var logger = LoggerFactory.getLogger(this.javaClass)

    protected lateinit var userWatcher: User

    protected lateinit var userCommitter: User

    protected lateinit var userAdmin: User

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var gitService: GitService

    @Autowired
    protected lateinit var lockService: LockService

    @Autowired
    protected lateinit var configurationService: DefaultConfigurationService

    @BeforeEach
    fun initUsers() {
        logger.info("Initializing Users")
        SecurityContextHolder.getContext().authentication = null
        userService.removeAll()
        userWatcher = userService.save(User("Watcher", "User", "watcher@example.com", Role.WATCHER), "watcher")
        userCommitter =
            userService.save(User("Committer", "User", "committer@example.com", Role.COMMITTER), "committer")
        userAdmin = userService.save(User("Admin", "User", "admin@example.com", Role.ADMIN), "admin")
    }

    @BeforeEach
    @Throws(IOException::class)
    fun resetRepository() {
        logger.info("Resetting repository")
        val repositoryPath = gitService.repositoryPath
        Files.walk(repositoryPath)
            .sorted(Comparator.reverseOrder())
            .map { obj: Path -> obj.toFile() }
            .forEach { obj: File -> obj.delete() }
        val repository = FileRepositoryBuilder.create(repositoryPath.resolve(".git").toFile())
        repository.create()
        lockService.clear()
    }

    protected fun setUser(user: UserDetails?) {
        if (null == user) {
            SecurityContextHolder.getContext().authentication = null
            return
        }
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
}