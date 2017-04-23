package net.dontdrinkandroot.gitki.wicket;

import net.dontdrinkandroot.gitki.wicket.page.HomePage;
import net.dontdrinkandroot.gitki.wicket.resource.ExternalJQueryResourceReference;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.resource.JQueryResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class WebApplication extends AuthenticatedWebApplication implements ApplicationContextAware
{
    private ApplicationContext applicationContext;

    @Override
    protected void init()
    {
        super.init();

        this.getMarkupSettings().setStripWicketTags(true);
        this.getComponentInstantiationListeners().add(new SpringComponentInjector(this, this.applicationContext, true));
        this.getJavaScriptLibrarySettings().setJQueryReference(new ExternalJQueryResourceReference());
    }

    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass()
    {
        return WebSession.class;
    }

    protected Class<? extends WebPage> getSignInPageClass()
    {
        return null;
    }

    public Class<? extends Page> getHomePage()
    {
        return HomePage.class;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }
}
