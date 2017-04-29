package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Test;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class StartPageTest extends AbstractWicketTest
{
    @Test
    public void testRendering()
    {
        this.getWicketTester().startPage(DirectoryPage.class, new PageParameters());
        this.getWicketTester().assertRenderedPage(DirectoryPage.class);
    }
}
