package net.dontdrinkandroot.gitki.wicket.page.directory;

import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryPageTest extends AbstractWicketTest
{
    @Test
    public void testAccess()
    {
        this.loadDefaultUsers();
        this.assertPageAccessible(DirectoryPage.class, null);
        this.assertPageAccessible(DirectoryPage.class, this.userWatcher);
        this.assertPageAccessible(DirectoryPage.class, this.userCommitter);
        this.assertPageAccessible(DirectoryPage.class, this.userAdmin);

        this.configurationService.setAnonymousBrowsingEnabled(false);
        this.setUser(null);
        this.assertLoginRequired(DirectoryPage.class);
    }
}
