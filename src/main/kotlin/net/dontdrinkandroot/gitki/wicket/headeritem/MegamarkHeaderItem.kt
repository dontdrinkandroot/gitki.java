package net.dontdrinkandroot.gitki.wicket.headeritem

import net.dontdrinkandroot.gitki.wicket.getGitkiApplication
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem
import org.apache.wicket.request.Url
import org.apache.wicket.request.resource.UrlResourceReference

class MegamarkHeaderItem : JavaScriptReferenceHeaderItem(
    UrlResourceReference(
        Url.parse(
            getGitkiApplication()
                .servletContext
                .contextPath + "/lib/megamark/dist/megamark.min.js"
        )
    ),
    null,
    "megamark.js"
)