package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.model.CurrentUserFullNameModel;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.component.item.SimpleDropDownItem;
import org.apache.wicket.markup.repeater.RepeatingView;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.WATCHER)
public class UserDropdownItem extends SimpleDropDownItem
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
