package net.dontdrinkandroot.gitki.wicket.head

import net.dontdrinkandroot.gitki.wicket.getGitkiApplication
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem
import org.apache.wicket.request.Url
import org.apache.wicket.request.resource.UrlResourceReference

class DomadorHeaderItem : JavaScriptReferenceHeaderItem(
    UrlResourceReference(
        Url.parse(
            getGitkiApplication()
                .servletContext
                .contextPath + "/lib/domador/dist/domador.min.js"
        )
    ),
    null,
    "domador.js"
)