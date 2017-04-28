package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class SignInPage extends net.dontdrinkandroot.wicket.extras.page.SignInPage
{
    public SignInPage(PageParameters parameters)
    {
        super(parameters);
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
}
