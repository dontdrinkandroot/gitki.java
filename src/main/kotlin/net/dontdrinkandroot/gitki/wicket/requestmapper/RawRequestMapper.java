package net.dontdrinkandroot.gitki.wicket.requestmapper;

import org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class RawRequestMapper extends AbstractBookmarkableMapper
{
    @Override
    protected UrlInfo parseRequest(Request request) {
        return null;
    }

    @Override
    protected Url buildUrl(UrlInfo info) {
        return null;
    }

    @Override
    protected boolean pageMustHaveBeenCreatedBookmarkable() {
        return false;
    }
}
