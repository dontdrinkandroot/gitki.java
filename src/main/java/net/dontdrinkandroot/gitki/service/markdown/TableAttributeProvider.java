package net.dontdrinkandroot.gitki.service.markdown;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.Map;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class TableAttributeProvider implements AttributeProvider
{
    @Override
    public void setAttributes(Node node, String tagName, Map<String, String> attributes)
    {
        if (node instanceof TableBlock) {
            attributes.put("class", "table");
        }
    }
}
