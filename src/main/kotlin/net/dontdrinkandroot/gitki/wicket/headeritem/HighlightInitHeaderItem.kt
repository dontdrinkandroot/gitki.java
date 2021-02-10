package net.dontdrinkandroot.gitki.wicket.headeritem

import org.apache.wicket.markup.head.HeaderItem
import org.apache.wicket.markup.head.JavaScriptContentHeaderItem

class HighlightInitHeaderItem : JavaScriptContentHeaderItem("hljs.initHighlightingOnLoad();", "highlight-init") {

    override fun getDependencies(): List<HeaderItem> {
        return listOf(
            HightlightCssHeaderItem(),
            HighlightJsHeaderItem()
        )
    }
}