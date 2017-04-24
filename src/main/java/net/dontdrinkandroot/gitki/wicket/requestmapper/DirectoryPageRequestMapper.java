package net.dontdrinkandroot.gitki.wicket.requestmapper;

import net.dontdrinkandroot.gitki.wicket.page.DirectoryPage;
import org.apache.wicket.core.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.component.IRequestablePage;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryPageRequestMapper implements IRequestMapper
{
    @Override
    public IRequestHandler mapRequest(Request request)
    {
        return null;
    }

    @Override
    public int getCompatibilityScore(Request request)
    {
        return 0;
    }

    @Override
    public Url mapHandler(IRequestHandler requestHandler)
    {
        if (requestHandler instanceof RenderPageRequestHandler) {
            Class<? extends IRequestablePage> pageClass = ((RenderPageRequestHandler) requestHandler).getPageClass();
            if (DirectoryPage.class.isAssignableFrom(pageClass)) {
                return Url.parse("browse");
            }
        }

        return null;
    }
}
