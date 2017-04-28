package net.dontdrinkandroot.gitki.wicket;

import net.dontdrinkandroot.gitki.model.User;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class GitkiWebSession extends WebSession
{
    @Inject
    @Named("authenticationManager")
    private AuthenticationManager authenticationManager;

    public GitkiWebSession(Request request)
    {
        super(request);
        Injector.get().inject(this);
    }

    public boolean signIn(String email, String password)
    {
        try {
            Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (authentication.isAuthenticated()) {
                this.bind();
                return true;
            }
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            /* Noop */
        }

        return false;
    }

    public User getUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication
                || null == authentication.getPrincipal()
                || !(authentication.getPrincipal() instanceof User)) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public static GitkiWebSession get()
    {
        return (GitkiWebSession) WebSession.get();
    }
}
