package net.dontdrinkandroot.gitki.wicket.page.user;

import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import org.junit.jupiter.api.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserEditPageTest extends AbstractWicketTest
{
    @Test
    public void testAccess()
    {
        this.assertLoginRequired(UserEditPage.class);
        this.assertPageAccessible(UserEditPage.class, this.userWatcher);
        this.assertPageAccessible(UserEditPage.class, this.userCommitter);
        this.assertPageAccessible(UserEditPage.class, this.userAdmin);
    }
}
