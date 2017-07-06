package net.dontdrinkandroot.gitki.test;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.configuration.DefaultConfigurationService;
import net.dontdrinkandroot.gitki.service.user.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    protected UserDetails userWatcher;

    protected UserDetails userCommitter;

    protected UserDetails userAdmin;

    @Autowired
    protected UserService userService;

    @Autowired
    protected DefaultConfigurationService configurationService;

    protected void loadDefaultUsers()
    {
        User user;

        try {
            this.userWatcher = this.userService.loadUserByUsername("watcher@example.com");
        } catch (UsernameNotFoundException e) {
            user = new User("Watcher", "User", "watcher@example.com", Role.WATCHER);
            this.userWatcher = this.userService.save(user, "watcher");
        }

        try {
            this.userCommitter = this.userService.loadUserByUsername("committer@example.com");
        } catch (UsernameNotFoundException e) {
            user = new User("Committer", "User", "committer@example.com", Role.COMMITTER);
            this.userCommitter = this.userService.save(user, "committer");
        }

        try {
            this.userAdmin = this.userService.loadUserByUsername("admin@example.com");
        } catch (UsernameNotFoundException e) {
            user = new User("Admin", "User", "admin@example.com", Role.ADMIN);
            this.userAdmin = this.userService.save(user, "admin");
        }
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
