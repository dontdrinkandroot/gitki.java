package net.dontdrinkandroot.gitki.wicket.headeritem;

import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class HightlightCssHeaderItem extends CssUrlReferenceHeaderItem
{
    public HightlightCssHeaderItem()
    {
        super("https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.11.0/styles/default.min.css", null, null);
    }
}
