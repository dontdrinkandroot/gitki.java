package net.dontdrinkandroot.gitki.test;

import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = {"classpath:spring/context.test.xml"})
public abstract class AbstractWicketTest extends AbstractGitTest
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
