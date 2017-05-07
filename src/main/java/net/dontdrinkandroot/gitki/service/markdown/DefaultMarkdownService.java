package net.dontdrinkandroot.gitki.service.markdown;

import org.apache.commons.lang3.StringUtils;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DefaultMarkdownService implements MarkdownService
{
    private final Parser parser;
    private final HtmlRenderer renderer;

    public DefaultMarkdownService()
    {
        List<Extension> extensions = Arrays.asList(TablesExtension.create());
        this.parser = Parser.builder().extensions(extensions).build();
        this.renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .attributeProviderFactory(new GitkiAttributeProviderFactory())
                .build();
    }

    @Override
    public String parseToHtml(String source)
    {
        if (StringUtils.isEmpty(source)) {
            return "";
        }

        return this.renderer.render(this.parse(source));
    }

    public Node parse(String source)
    {
        return this.parser.parse(source);
    }
}
