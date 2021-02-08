package net.dontdrinkandroot.gitki.wicket.headeritem;

import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class WoofmarkHeaderItem extends JavaScriptReferenceHeaderItem
{
    public WoofmarkHeaderItem() {
        super(
                new UrlResourceReference(
                        Url.parse(GitkiWebApplication.get()
                                .getServletContext()
                                .getContextPath() + "/lib/woofmark/dist/woofmark.js")
                ),
                null,
                "woofmark.js"
        );
    }

    @Override
    public List<HeaderItem> getDependencies() {
        return Arrays.asList(
                new MegamarkHeaderItem(),
                new DomadorHeaderItem()
        );
    }
}
