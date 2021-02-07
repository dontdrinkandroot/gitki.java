package net.dontdrinkandroot.gitki.test;

import net.dontdrinkandroot.gitki.Application;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.configuration.DefaultConfigurationService;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.service.lock.LockService;
import net.dontdrinkandroot.gitki.service.user.UserService;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
public abstract class AbstractIntegrationTest
{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected User userWatcher;

    protected User userCommitter;

    protected User userAdmin;

    @Autowired
    protected UserService userService;

    @Autowired
    protected GitService gitService;

    @Autowired
    protected LockService lockService;

    @Autowired
    protected DefaultConfigurationService configurationService;

    @BeforeEach
    public void initUsers()
    {
        this.logger.info("Initializing Users");

        SecurityContextHolder.getContext().setAuthentication(null);

        this.userService.removeAll();

        User user;

        user = new User("Watcher", "User", "watcher@example.com", Role.WATCHER);
        this.userWatcher = this.userService.save(user, "watcher");

        user = new User("Committer", "User", "committer@example.com", Role.COMMITTER);
        this.userCommitter = this.userService.save(user, "committer");

        user = new User("Admin", "User", "admin@example.com", Role.ADMIN);
        this.userAdmin = this.userService.save(user, "admin");
    }

    @BeforeEach
    public void resetRepository() throws IOException
    {
        this.logger.info("Resetting repository");

        Path repositoryPath = this.gitService.getRepositoryPath();
        Files.walk(repositoryPath)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        Repository repository = FileRepositoryBuilder.create(repositoryPath.resolve(".git").toFile());
        repository.create();

        this.lockService.clear();
    }

    protected void setUser(UserDetails user)
    {
        if (null == user) {
            SecurityContextHolder.getContext().setAuthentication(null);
            return;
        }

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }
}
