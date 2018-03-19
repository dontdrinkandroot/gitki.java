package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.admin.ConfigurationPage;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import org.apache.wicket.Page;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.ADMIN)
public class ConfigurationPageItem extends BookmarkablePageLinkItem<Void>
{
    public <C extends Page> ConfigurationPageItem(String id)
    {
        super(id, new StringResourceModel("gitki.configuration"), ConfigurationPage.class);
    }
}
