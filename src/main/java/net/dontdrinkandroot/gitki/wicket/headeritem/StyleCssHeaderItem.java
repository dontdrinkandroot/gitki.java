package net.dontdrinkandroot.gitki.wicket.headeritem;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class StyleCssHeaderItem extends CssReferenceHeaderItem
{
    public StyleCssHeaderItem()
    {
        super(
                new UrlResourceReference(Url.parse("css/style.css")).setContextRelative(true),
                null,
                null,
                null
        );
    }
}
