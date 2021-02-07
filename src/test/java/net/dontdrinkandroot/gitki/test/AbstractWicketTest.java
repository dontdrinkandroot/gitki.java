package net.dontdrinkandroot.gitki.test;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.page.SignInPage;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class AbstractWicketTest extends AbstractIntegrationTest
{
    @Autowired
    protected GitkiWebApplication wicketApplication;

    private WicketTester wicketTester;

    @BeforeEach
    public void before()
    {
        this.wicketTester = new WicketTester(this.wicketApplication);
    }

    public WicketTester getWicketTester()
    {
        return this.wicketTester;
    }

    protected void assertLoginRequired(Class<? extends Page> pageClass)
    {
        this.assertLoginRequired(pageClass, new PageParameters());
    }

    protected void assertLoginRequired(Class<? extends Page> pageClass, PageParameters parameters)
    {
        this.wicketTester.startPage(pageClass, parameters);
        this.wicketTester.assertRenderedPage(SignInPage.class);
    }

    protected <P extends Page> P assertPageAccessible(Class<P> pageClass, User user, Object... parameters)
    {
        GitkiWebSession.get().invalidate();
        if (null != user) {
            GitkiWebSession.get().signIn(user);
        }
        P page = this.startPage(pageClass, parameters);
        this.wicketTester.assertRenderedPage(pageClass);

        return page;
    }

    protected void assertPageInaccessible(Class<? extends Page> pageClass, User user, Object... parameters)
    {
        GitkiWebSession.get().invalidate();
        if (null != user) {
            GitkiWebSession.get().signIn(user);
        }
        try {
            this.startPage(pageClass, parameters);
            Assertions.fail("UnauthorizedInstantiationException expected");
        } catch (UnauthorizedInstantiationException e) {
            /* Expected */
        }
    }

    protected <C extends Page> C startPage(Class<C> pageClass, Object[] parameters)
    {
        if (null == parameters || 0 == parameters.length) {
            return this.wicketTester.startPage(pageClass);
        }

        if (1 == parameters.length && parameters[0] instanceof PageParameters) {
            return this.wicketTester.startPage(pageClass, (PageParameters) parameters[0]);
        }

        try {
            C page = ConstructorUtils.invokeConstructor(pageClass, parameters);
            return this.wicketTester.startPage(page);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            if (e instanceof InvocationTargetException) {
                InvocationTargetException invocationTargetException = (InvocationTargetException) e;
                if (invocationTargetException.getTargetException() instanceof UnauthorizedInstantiationException) {
                    throw (UnauthorizedInstantiationException) invocationTargetException.getTargetException();
                }
            }
            throw new RuntimeException(e);
        }
    }
}
