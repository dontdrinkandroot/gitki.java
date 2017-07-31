package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.wicket.model.CurrentUserFullNameModel;
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@AuthorizeAction(action = Action.RENDER, roles = {"WATCHER"})
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
