package net.dontdrinkandroot.gitki.wicket;

import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication;
import net.dontdrinkandroot.gitki.wicket.page.FirstRunPage;
import net.dontdrinkandroot.gitki.wicket.page.HistoryPage;
import net.dontdrinkandroot.gitki.wicket.page.SignInPage;
import net.dontdrinkandroot.gitki.wicket.page.configuration.ConfigurationPage;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage;
import net.dontdrinkandroot.gitki.wicket.page.user.UserListPage;
import net.dontdrinkandroot.gitki.wicket.requestmapper.BrowseRequestMapper;
import net.dontdrinkandroot.gitki.wicket.requestmapper.RawRequestMapper;
import net.dontdrinkandroot.gitki.wicket.resource.ExternalJQueryResourceReference;
import net.dontdrinkandroot.gitki.wicket.resource.RawResource;
import net.dontdrinkandroot.gitki.wicket.security.GitkiAuthorizationStrategy;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.springframework.stereotype.Component;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Component
public class GitkiWebApplication extends WicketBootSecuredWebApplication
{
    @Override
    protected void init()
    {
        super.init();

        this.getJavaScriptLibrarySettings().setJQueryReference(new ExternalJQueryResourceReference());

        this.getSecuritySettings()
                .setAuthorizationStrategy(this.getApplicationContext().getBean(GitkiAuthorizationStrategy.class));

        BrowseRequestMapper browseRequestMapper = new BrowseRequestMapper();
        Injector.get().inject(browseRequestMapper);
        this.mount(browseRequestMapper);

        this.mount(new RawRequestMapper());
        this.mountPage("signin", SignInPage.class);
        this.mountPage("firstrun", FirstRunPage.class);
        this.mountPage("configuration", ConfigurationPage.class);
        this.mountPage("history", HistoryPage.class);
        this.mountPage("users", UserListPage.class);
        this.mountPage("users/edit", UserEditPage.class);

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
}
