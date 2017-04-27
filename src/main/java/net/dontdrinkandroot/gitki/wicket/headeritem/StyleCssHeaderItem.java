package net.dontdrinkandroot.gitki.wicket.headeritem;

import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.request.resource.ContextRelativeResourceReference;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class StyleCssHeaderItem extends CssReferenceHeaderItem
{
    public StyleCssHeaderItem()
    {
        super(new ContextRelativeResourceReference("css/style.css", false), null, null, null);
    }
}
