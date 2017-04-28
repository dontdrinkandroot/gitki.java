package net.dontdrinkandroot.gitki.wicket;

import net.dontdrinkandroot.gitki.wicket.page.SignInPage;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.requestmapper.BrowseRequestMapper;
import net.dontdrinkandroot.gitki.wicket.requestmapper.RawRequestMapper;
import net.dontdrinkandroot.gitki.wicket.resource.ExternalJQueryResourceReference;
import net.dontdrinkandroot.gitki.wicket.resource.RawResource;
import net.dontdrinkandroot.gitki.wicket.security.RoleAuthorizationStrategy;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class GitkiWebApplication extends WebApplication implements ApplicationContextAware, IUnauthorizedComponentInstantiationListener
{
    private ApplicationContext applicationContext;

    @Override
    protected void init()
    {
        super.init();

        this.getMarkupSettings().setStripWicketTags(true);
        this.getComponentInstantiationListeners().add(new SpringComponentInjector(this, this.applicationContext, true));
        this.getJavaScriptLibrarySettings().setJQueryReference(new ExternalJQueryResourceReference());

        this.getSecuritySettings().setAuthorizationStrategy(new RoleAuthorizationStrategy());
        this.getSecuritySettings().setUnauthorizedComponentInstantiationListener(this);

        BrowseRequestMapper browseRequestMapper = new BrowseRequestMapper();
        Injector.get().inject(browseRequestMapper);
        this.mount(browseRequestMapper);

        this.mount(new RawRequestMapper());
        this.mountPage("login", SignInPage.class);

        RawResource rawResource = new RawResource();
        Injector.get().inject(rawResource);
        this.getSharedResources().add("raw", rawResource);
        this.mountResource("raw", new SharedResourceReference("raw"));
    }

    @Override
    public Class<? extends Page> getHomePage()
    {
        return DirectoryPage.class;
    }

    @Override
    public Session newSession(Request request, Response response)
    {
        return new GitkiWebSession(request);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onUnauthorizedInstantiation(Component component)
    {
        if (component instanceof Page && null == GitkiWebSession.get().getUser()) {
            throw new RestartResponseAtInterceptPageException(SignInPage.class);
        }

        throw new UnauthorizedInstantiationException(component.getClass());
    }
}
