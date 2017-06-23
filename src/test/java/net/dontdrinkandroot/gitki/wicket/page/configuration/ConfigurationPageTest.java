package net.dontdrinkandroot.gitki.wicket.page.configuration;

import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import net.dontdrinkandroot.gitki.wicket.page.SignInPage;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class ConfigurationPageTest extends AbstractWicketTest
{
    @Test
    public void testLoginRequired()
    {
        this.getWicketTester().startPage(ConfigurationPage.class);
        this.getWicketTester().assertRenderedPage(SignInPage.class);
    }
}
