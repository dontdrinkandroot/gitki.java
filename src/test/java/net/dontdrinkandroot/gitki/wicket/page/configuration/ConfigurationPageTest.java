package net.dontdrinkandroot.gitki.wicket.page.configuration;

import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class ConfigurationPageTest extends AbstractWicketTest
{
    @Test
    public void testAccess()
    {
        this.addDefaultUsers();
        this.assertLoginRequired(ConfigurationPage.class);
        this.assertPageInaccessible(ConfigurationPage.class, this.userWatcher);
        this.assertPageInaccessible(ConfigurationPage.class, this.userCommitter);
        this.assertPageAccessible(ConfigurationPage.class, this.userAdmin);
    }
}
