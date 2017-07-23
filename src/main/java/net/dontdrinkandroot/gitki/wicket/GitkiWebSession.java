package net.dontdrinkandroot.gitki.wicket;

import com.giffing.wicket.spring.boot.starter.configuration.extensions.external.spring.security.SecureWebSession;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService;
import org.apache.wicket.authorization.UnauthorizedInstantiationException;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.ZoneId;
import java.util.Locale;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class GitkiWebSession extends SecureWebSession
{
    @SpringBean
    private ConfigurationService configurationService;

    public GitkiWebSession(Request request)
    {
        super(request);
    }

    public static GitkiWebSession get()
    {
        return (GitkiWebSession) WebSession.get();
    }

    public User getUser()
    {
        if (!this.isSignedIn()) {
            return null;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (null != principal && principal instanceof User) {
            return (User) principal;
        }

        this.signOut();
        return null;
    }

    /**
     * Manually sign in a specific user (useful for tests).
     *
     * @param user The user to sign in.
     */
    public void signIn(User user)
    {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        this.signIn(true);
    }

    public <T extends IRequestableComponent> void assertAnonymousBrowsing(Class<T> componentClass)
    {
        if (!this.checkAnonymousBrowsing()) {
            throw new UnauthorizedInstantiationException(componentClass);
        }
    }

    public boolean checkAnonymousBrowsing()
    {
        if (this.configurationService.isAnonymousBrowsingEnabled()) {
            return true;
        }

        User user = this.getUser();
        return null != user && user.getRole().containsRole(Role.WATCHER);
    }

    @Override
    public Locale getLocale()
    {
        User user = this.getUser();
        if (null != user) {
            return user.getLocale();
        }

        return super.getLocale();
    }

    public ZoneId getZoneId()
    {
        User user = this.getUser();
        if (null != user) {
            return ZoneId.of(user.getZoneId());
        }

        return ZoneId.of("UTC");
    }

    public boolean hasRole(Role role)
    {
        User user = this.getUser();
        return null != user && user.getRole().containsRole(role);
    }
}
