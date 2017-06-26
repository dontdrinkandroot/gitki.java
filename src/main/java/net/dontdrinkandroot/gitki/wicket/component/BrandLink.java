package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class BrandLink extends BookmarkablePageLink<Void>
{
    @SpringBean
    private ConfigurationService configurationService;

    public <C extends Page> BrandLink(String id)
    {
        super(id, GitkiWebApplication.get().getHomePage());
        this.setBody(Model.of(this.configurationService.getName()));
    }
}
