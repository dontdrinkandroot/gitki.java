package net.dontdrinkandroot.gitki.wicket.requestmapper;

import net.dontdrinkandroot.gitki.wicket.page.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper;
import org.apache.wicket.core.request.mapper.MapperUtils;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.info.PageComponentInfo;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class BrowseRequestMapper extends AbstractBookmarkableMapper
{
    @Override
    protected UrlInfo parseRequest(Request request)
    {
        Url url = request.getUrl();
        List<String> segments = url.getSegments();
        if (segments.size() < 1 || !segments.get(0).equals("browse")) {
            return null;
        }

        PageComponentInfo pageComponentInfo = MapperUtils.getPageComponentInfo(request.getUrl());

        String lastSegment = segments.get(segments.size() - 1);

        /* Directory Page */
        if ("".equals(lastSegment)) {
            PageParameters parameters = new PageParameters();
            for (int i = 1; i < segments.size(); i++) {
                parameters.set(i - 1, segments.get(i));
            }

            return new UrlInfo(pageComponentInfo, DirectoryPage.class, parameters);
        }

        /* File Page */
        PageParameters parameters = new PageParameters();
        for (int i = 1; i < segments.size(); i++) {
            parameters.set(i - 1, segments.get(i));
        }

        return new UrlInfo(pageComponentInfo, FilePage.class, parameters);
    }

    @Override
    protected Url buildUrl(UrlInfo info)
    {
        if (DirectoryPage.class.isAssignableFrom(info.getPageClass())) {
            Url url = new Url();
            url.getSegments().add("browse");

            this.encodePageComponentInfo(url, info.getPageComponentInfo());

            PageParameters copy = new PageParameters(info.getPageParameters());
            if (copy.getIndexedCount() == 0) {
                copy.set(0, "");
            }
            return this.encodePageParameters(url, copy, this.pageParametersEncoder);
        }

        if (FilePage.class.isAssignableFrom(info.getPageClass())) {
            Url url = new Url();
            url.getSegments().add("browse");

            this.encodePageComponentInfo(url, info.getPageComponentInfo());

            PageParameters copy = new PageParameters(info.getPageParameters());
            return this.encodePageParameters(url, copy, this.pageParametersEncoder);
        }

        return null;
    }

    @Override
    protected boolean pageMustHaveBeenCreatedBookmarkable()
    {
        return false;
    }
}
