package net.dontdrinkandroot.gitki.wicket.headeritem;

import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class MegamarkHeaderItem extends JavaScriptReferenceHeaderItem
{
    public MegamarkHeaderItem() {
        super(
                new UrlResourceReference(
                        Url.parse(GitkiWebApplication.get()
                                .getServletContext()
                                .getContextPath() + "/lib/megamark/dist/megamark.min.js")
                ),
                null,
                "megamark.js"
        );
    }
}
