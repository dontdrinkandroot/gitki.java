package net.dontdrinkandroot.gitki.test;

import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class AbstractWicketTest extends AbstractIntegrationTest
{
    @Autowired
    protected GitkiWebApplication wicketApplication;

    private WicketTester wicketTester;

    @Before
    public void before()
    {
        this.wicketTester = new WicketTester(this.wicketApplication);
    }

    public WicketTester getWicketTester()
    {
        return this.wicketTester;
    }
}
