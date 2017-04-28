package net.dontdrinkandroot.gitki.service.markdown;

import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class GitkiAttributeProviderFactory implements AttributeProviderFactory
{
    @Override
    public AttributeProvider create(AttributeProviderContext context)
    {
        return new TableAttributeProvider();
    }
}
