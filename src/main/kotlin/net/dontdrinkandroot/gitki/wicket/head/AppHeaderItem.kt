package net.dontdrinkandroot.gitki.wicket.head

import org.apache.wicket.markup.head.CssUrlReferenceHeaderItem
import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem

class RuntimeJsHeaderItem : JavaScriptUrlReferenceHeaderItem("build/runtime.js", "runtime.js")

class AppJsHeaderItem : JavaScriptUrlReferenceHeaderItem("build/app.js", "app.js") {

    override fun getDependencies() = listOf(RuntimeJsHeaderItem())
}

class AppCssHeaderItem : CssUrlReferenceHeaderItem("build/app.css", null) {

    override fun getDependencies() = listOf(AppJsHeaderItem())
}
