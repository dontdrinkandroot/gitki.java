package net.dontdrinkandroot.gitki.wicket.resource;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class ExternalJQueryResourceReference extends UrlResourceReference
{
    public ExternalJQueryResourceReference()
    {
        super(Url.parse("https://code.jquery.com/jquery-2.2.4.min.js"));
    }
}
