package net.dontdrinkandroot.gitki.service.markdown

import org.commonmark.ext.gfm.tables.TableBlock
import org.commonmark.node.Node
import org.commonmark.renderer.html.AttributeProvider

class TableAttributeProvider : AttributeProvider {

    override fun setAttributes(node: Node, tagName: String, attributes: MutableMap<String, String>) {
        if (node is TableBlock) attributes["class"] = "table"
    }
}