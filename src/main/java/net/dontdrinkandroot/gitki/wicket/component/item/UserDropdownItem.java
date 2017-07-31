package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.model.CurrentUserFullNameModel;
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.WATCHER)
public class UserDropdownItem extends RepeatingDropdownItem
{
    public UserDropdownItem(String id)
    {
        super(id, new CurrentUserFullNameModel());
    }

    @Override
    protected void populateItems(RepeatingView itemView)
    {
        itemView.add(new BookmarkablePageLinkItem<>(itemView.newChildId(), new StringResourceModel("gitki.profile"),
                UserEditPage.class
        ));
        itemView.add(new SignoutItem(itemView.newChildId()));
    }
}
