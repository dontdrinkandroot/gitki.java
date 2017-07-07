package net.dontdrinkandroot.gitki.service;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.test.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserServiceTest extends AbstractIntegrationTest
{
    @Test
    public void testHasAdminUser()
    {
        User user = (User) this.userService.loadUserByUsername("admin@example.com");
        Assert.assertTrue(this.userService.hasAdminUser());
        this.userService.remove(user);
        Assert.assertFalse(this.userService.hasAdminUser());
    }
}
