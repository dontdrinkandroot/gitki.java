package net.dontdrinkandroot.gitki.service.markdown

import org.apache.commons.lang3.StringUtils
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.ext.gfm.tables.TablesExtension
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultMarkdownService : MarkdownService {

    private val parser: Parser

    private val renderer: HtmlRenderer

    init {
        val extensions = Arrays.asList(TablesExtension.create(), StrikethroughExtension.create())
        parser = Parser.builder().extensions(extensions).build()
        renderer = HtmlRenderer.builder()
            .extensions(extensions)
            .attributeProviderFactory(GitkiAttributeProviderFactory())
            .build()
    }

    override fun parseToHtml(source: String): String =
        if (StringUtils.isEmpty(source)) "" else renderer.render(parse(source))

    fun parse(source: String?): Node = parser.parse(source)
}