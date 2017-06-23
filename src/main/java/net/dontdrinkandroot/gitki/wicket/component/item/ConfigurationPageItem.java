package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.configuration.ConfigurationPage;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import org.apache.wicket.Page;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.ADMIN)
public class ConfigurationPageItem extends BookmarkablePageLinkItem
{
    public <C extends Page> ConfigurationPageItem(String id)
    {
        super(id, Model.of("Configuration"), ConfigurationPage.class);
    }
}
