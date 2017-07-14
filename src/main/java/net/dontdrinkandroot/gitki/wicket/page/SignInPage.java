package net.dontdrinkandroot.gitki.wicket.page;

import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage;
import net.dontdrinkandroot.gitki.service.user.UserService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.headeritem.StyleCssHeaderItem;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@WicketSignInPage
public class SignInPage extends net.dontdrinkandroot.wicket.bootstrap.page.SignInPage
{
    @SpringBean
    private UserService userService;

    public SignInPage(PageParameters parameters)
    {
        super(parameters);
    }

    @Override
    protected void onInitialize()
    {
        if (!this.userService.hasAdminUser()) {
            throw new RestartResponseAtInterceptPageException(FirstRunPage.class);
        }

        super.onInitialize();
    }

    @Override
    protected IModel<String> createUsernameLabelModel()
    {
        return Model.of("Email");
    }

    @Override
    protected boolean isSignedIn()
    {
        return null != GitkiWebSession.get().getUser();
    }

    @Override
    protected boolean signIn(String username, String password)
    {
        return GitkiWebSession.get().signIn(username, password);
    }

    @Override
    protected HeaderItem getBootstrapCssHeaderItem()
    {
        return new StyleCssHeaderItem();
    }
}
