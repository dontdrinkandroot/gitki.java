package net.dontdrinkandroot.gitki.wicket.resource;

import org.apache.wicket.markup.head.JavaScriptUrlReferenceHeaderItem;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;

import java.net.URL;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class ExternalJQueryResourceReference extends UrlResourceReference
{
    public ExternalJQueryResourceReference()
    {
        super(Url.parse("https://code.jquery.com/"));
    }
}
