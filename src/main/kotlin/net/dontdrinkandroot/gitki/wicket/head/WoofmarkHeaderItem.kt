package net.dontdrinkandroot.gitki.wicket.head

import net.dontdrinkandroot.gitki.wicket.getGitkiApplication
import org.apache.wicket.markup.head.HeaderItem
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem
import org.apache.wicket.request.Url
import org.apache.wicket.request.resource.UrlResourceReference

class WoofmarkHeaderItem : JavaScriptReferenceHeaderItem(
    UrlResourceReference(
        Url.parse(
            getGitkiApplication()
                .servletContext
                .contextPath + "/lib/woofmark/dist/woofmark.js"
        )
    ),
    null,
    "woofmark.js"
) {

    override fun getDependencies(): List<HeaderItem> {
        return listOf(
            MegamarkHeaderItem(),
            DomadorHeaderItem()
        )
    }
}