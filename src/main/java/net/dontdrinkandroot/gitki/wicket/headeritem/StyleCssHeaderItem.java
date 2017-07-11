package net.dontdrinkandroot.gitki.wicket.headeritem;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class StyleCssHeaderItem extends CssReferenceHeaderItem
{
    public StyleCssHeaderItem()
    {
        super(new PackageResourceReference(StyleCssHeaderItem.class, "style.css"), null, null, null);
    }
}
