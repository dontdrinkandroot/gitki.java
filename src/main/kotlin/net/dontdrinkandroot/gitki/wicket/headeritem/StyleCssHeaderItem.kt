package net.dontdrinkandroot.gitki.wicket.headeritem

import org.apache.wicket.markup.head.CssReferenceHeaderItem
import org.apache.wicket.request.Url
import org.apache.wicket.request.resource.UrlResourceReference

class StyleCssHeaderItem : CssReferenceHeaderItem(
    UrlResourceReference(Url.parse("css/style.css")).setContextRelative(true),
    null,
    null,
    null
)