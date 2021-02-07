package net.dontdrinkandroot.gitki.wicket.headeritem;

import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class HighlightJsHeaderItem extends JavaScriptUrlReferenceHeaderItem
{
    public HighlightJsHeaderItem()
    {
        super(
                "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js",
                "highlight.js"
        );
    }
}
