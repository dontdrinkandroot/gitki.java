package net.dontdrinkandroot.gitki.test;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.configuration.DefaultConfigurationService;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.service.user.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"classpath:spring/context.xml"})
public abstract class AbstractIntegrationTest
{
    protected User userWatcher;

    protected User userCommitter;

    protected User userAdmin;

    @Autowired
    protected UserService userService;

    @Autowired
    protected GitService gitService;

    @Autowired
    protected DefaultConfigurationService configurationService;

    @Before
    public void beforeMethod()
    {
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
