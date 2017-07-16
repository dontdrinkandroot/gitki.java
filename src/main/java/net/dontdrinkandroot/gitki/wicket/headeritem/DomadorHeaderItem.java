package net.dontdrinkandroot.gitki.wicket.headeritem;

import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DomadorHeaderItem extends JavaScriptReferenceHeaderItem
{
    public DomadorHeaderItem()
    {
        super(
                new UrlResourceReference(
                        Url.parse(GitkiWebApplication.get()
                                .getServletContext()
                                .getContextPath() + "/lib/domador/dist/domador.min.js")
                ),
                null,
                "domador.js",
                false,
                null,
                null
        );
    }
}
