package net.dontdrinkandroot.gitki.wicket.headeritem

import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem

class HighlightJsHeaderItem : JavaScriptUrlReferenceHeaderItem(
    "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.12.0/highlight.min.js",
    "highlight.js"
)