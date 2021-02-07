package net.dontdrinkandroot.gitki.service;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.test.AbstractIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserServiceTest extends AbstractIntegrationTest
{
    @Test
    public void testHasAdminUser()
    {
        User user = (User) this.userService.loadUserByUsername("admin@example.com");
        Assertions.assertTrue(this.userService.hasAdminUser());
        this.userService.remove(user);
        Assertions.assertFalse(this.userService.hasAdminUser());
    }
}
