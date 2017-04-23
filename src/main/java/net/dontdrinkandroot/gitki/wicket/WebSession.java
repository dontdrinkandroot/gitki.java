package net.dontdrinkandroot.gitki.wicket;

import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class WebSession extends AuthenticatedWebSession
{
    public WebSession(Request request)
    {
        super(request);
    }

    protected boolean authenticate(String username, String password)
    {
        return false;
    }

    public Roles getRoles()
    {
        return null;
    }
}
