package net.dontdrinkandroot.gitki.wicket.page.user;

import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserListPageTest extends AbstractWicketTest
{
    @Test
    public void testAccess()
    {
        this.assertLoginRequired(UserListPage.class);
        this.assertPageInaccessible(UserListPage.class, this.userWatcher);
        this.assertPageInaccessible(UserListPage.class, this.userCommitter);
        this.assertPageAccessible(UserListPage.class, this.userAdmin);
    }
}
