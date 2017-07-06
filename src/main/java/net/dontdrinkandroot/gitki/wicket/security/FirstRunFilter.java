package net.dontdrinkandroot.gitki.wicket.security;

import net.dontdrinkandroot.gitki.service.user.UserService;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FirstRunFilter implements Filter
{
    private UserService userService;

    private boolean resolved = false;

    protected FirstRunFilter()
    {
        /* Reflection instantiation */
    }

    @Inject
    public FirstRunFilter(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        /* Noop */
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (!httpRequest.getRequestURI().startsWith("/firstrun") && !this.isResolved()) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/firstrun");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {
        /* Noop */
    }

    private boolean isResolved()
    {
        if (this.resolved) {
            return true;
        }

        if (this.userService.hasAdminUser()) {
            this.resolved = true;
            return true;
        }

        return false;
    }
}
