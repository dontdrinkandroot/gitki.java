package net.dontdrinkandroot.gitki.wicket.headeritem;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem;

import java.util.Arrays;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class HighlightInitHeaderItem extends JavaScriptContentHeaderItem
{
    public HighlightInitHeaderItem()
    {
        super("hljs.initHighlightingOnLoad();", "highlight-init");
    }

    @Override
    public List<HeaderItem> getDependencies()
    {
        return Arrays.asList(
                new HightlightCssHeaderItem(),
                new HighlightJsHeaderItem()
        );
    }
}
