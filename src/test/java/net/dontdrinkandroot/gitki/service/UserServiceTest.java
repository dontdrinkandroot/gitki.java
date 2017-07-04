package net.dontdrinkandroot.gitki.service;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.user.UserService;
import net.dontdrinkandroot.gitki.test.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserServiceTest extends AbstractIntegrationTest
{
    @Autowired
    private UserService userService;

    @Test
    public void testHasAdminUser()
    {
        Assert.assertFalse(this.userService.hasAdminUser());
        User user = new User("Admin", "User", "admin@example.com", Role.ADMIN);
        this.userService.save(user, "admin");
        Assert.assertTrue(this.userService.hasAdminUser());
    }
}
