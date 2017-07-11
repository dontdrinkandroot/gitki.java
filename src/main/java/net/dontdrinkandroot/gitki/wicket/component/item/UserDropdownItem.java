package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.wicket.model.CurrentUserFullNameModel;
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.repeater.RepeatingView;

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
        itemView.add(new LogoutItem(itemView.newChildId()));
    }
}
