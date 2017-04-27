package net.dontdrinkandroot.gitki.wicket.requestmapper;

import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.TextFileEditPage;
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage;
import net.dontdrinkandroot.gitki.wicket.page.file.view.TextViewPage;
import org.apache.commons.io.FilenameUtils;
import org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper;
import org.apache.wicket.core.request.mapper.MapperUtils;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.info.PageComponentInfo;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

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

        StringValue actionValue = url.getQueryParameterValue("action");
        if (!actionValue.isNull() && !actionValue.isEmpty()) {
            parameters.set("action", actionValue.toString());
        }

        return new UrlInfo(
                pageComponentInfo,
                this.resolveFilePageClass(lastSegment, actionValue),
                parameters
        );
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

    protected Class<? extends FilePage> resolveFilePageClass(String fileName, StringValue actionValue)
    {
        String action = actionValue.toString("view");

        Class<? extends FilePage> pageClass;
        switch (action) {
            case "view":
                pageClass = this.resolveFilePageViewClass(fileName);
                if (null != pageClass) {
                    return pageClass;
                }
                break;

            case "edit":
                pageClass = this.resolveFilePageEditClass(fileName);
                if (null != pageClass) {
                    return pageClass;
                }
                break;
        }

        return SimpleViewPage.class;
    }

    protected Class<? extends FilePage> resolveFilePageViewClass(String fileName)
    {
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension) {
            case "txt":
                return TextViewPage.class;
        }

        return null;
    }

    protected Class<? extends FilePage> resolveFilePageEditClass(String fileName)
    {
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension) {
            case "txt":
                return TextFileEditPage.class;
        }

        return null;
    }

    @Override
    protected boolean pageMustHaveBeenCreatedBookmarkable()
    {
        return false;
    }
}
