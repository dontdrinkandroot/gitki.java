package net.dontdrinkandroot.gitki.service.markdown

import org.commonmark.renderer.html.AttributeProvider
import org.commonmark.renderer.html.AttributeProviderContext
import org.commonmark.renderer.html.AttributeProviderFactory

class GitkiAttributeProviderFactory : AttributeProviderFactory {

    override fun create(context: AttributeProviderContext): AttributeProvider {
        return TableAttributeProvider()
    }
}