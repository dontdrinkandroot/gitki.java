package net.dontdrinkandroot.gitki.wicket.resource

import net.dontdrinkandroot.gitki.wicket.head.RuntimeJsHeaderItem
import org.apache.wicket.markup.head.HeaderItem
import org.apache.wicket.request.Url
import org.apache.wicket.request.resource.UrlResourceReference

class JQueryResourceReference : UrlResourceReference(Url.parse("build/jquery.js")) {

    init {
        isContextRelative = true
    }

    override fun getDependencies(): List<HeaderItem> = listOf(RuntimeJsHeaderItem())
}