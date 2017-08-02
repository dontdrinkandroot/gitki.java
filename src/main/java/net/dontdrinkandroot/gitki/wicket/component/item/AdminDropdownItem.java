package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.admin.ErrorTestPage;
import net.dontdrinkandroot.gitki.wicket.page.admin.LockListPage;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.ADMIN)
public class AdminDropdownItem extends RepeatingDropdownItem<Void>
{
    public AdminDropdownItem(String id)
    {
        super(id, new StringResourceModel("gitki.administration"));
    }

    @Override
    protected void populateItems(RepeatingView itemView)
    {
        itemView.add(new UserListPageItem(itemView.newChildId()));
        itemView.add(new ConfigurationPageItem(itemView.newChildId()));
        itemView.add(new BookmarkablePageLinkItem(itemView.newChildId(), new StringResourceModel("gitki.locks"),
                LockListPage.class
        ));
        itemView.add(new BookmarkablePageLinkItem(itemView.newChildId(), Model.of("Errors"), ErrorTestPage.class));
    }
}
