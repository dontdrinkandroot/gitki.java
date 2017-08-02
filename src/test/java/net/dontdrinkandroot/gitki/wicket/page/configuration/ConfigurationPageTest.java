package net.dontdrinkandroot.gitki.wicket.page.configuration;

import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import net.dontdrinkandroot.gitki.wicket.page.admin.ConfigurationPage;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class ConfigurationPageTest extends AbstractWicketTest
{
    @Test
    public void testAccess()
    {
        this.assertLoginRequired(ConfigurationPage.class);
        this.assertPageInaccessible(ConfigurationPage.class, this.userWatcher);
        this.assertPageInaccessible(ConfigurationPage.class, this.userCommitter);
        this.assertPageAccessible(ConfigurationPage.class, this.userAdmin);
    }
}
