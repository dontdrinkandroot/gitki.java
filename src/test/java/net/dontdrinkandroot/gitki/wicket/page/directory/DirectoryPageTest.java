package net.dontdrinkandroot.gitki.wicket.page.directory;

import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryPageTest extends AbstractWicketTest
{
    @Test
    public void testAccess()
    {
        this.assertPageAccessible(DirectoryPage.class, null);
        this.assertPageAccessible(DirectoryPage.class, this.userWatcher);
        this.assertPageAccessible(DirectoryPage.class, this.userCommitter);
        this.assertPageAccessible(DirectoryPage.class, this.userAdmin);

        this.configurationService.setAnonymousBrowsingEnabled(false);
        GitkiWebSession.get().invalidate();
        this.assertPageInaccessible(DirectoryPage.class, null);

        //TODO: Maybe refactor authorization strategy so we get redirected to login
        //this.assertLoginRequired(DirectoryPage.class);
    }
}
