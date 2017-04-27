package net.dontdrinkandroot.gitki.wicket;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class GitkiWebSession extends WebSession
{
    private User user;

    public GitkiWebSession(Request request)
    {
        super(request);
    }

    public boolean signIn(String email, String password)
    {
        if ("admin@example.com".equals(email) && "admin".equals(password)) {
            this.user = new User("Admin", "User", "admin@example.com", Role.ADMIN);
            this.bind();
            return true;
        }

        if ("committer@example.com".equals(email) && "committer".equals(password)) {
            this.user = new User("Committer", "User", "committer@example.com", Role.COMMITTER);
            this.bind();
            return true;
        }

        if ("watcher@example.com".equals(email) && "watcher".equals(password)) {
            this.user = new User("Watcher", "User", "watcher@example.com", Role.WATCHER);
            this.bind();
            return true;
        }

        return false;
    }

    public boolean isSignedIn()
    {
        return null != this.user;
    }

    public User getUser()
    {
        return this.user;
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
        this.user = null;
    }

    public static GitkiWebSession get()
    {
        return (GitkiWebSession) WebSession.get();
    }
}
