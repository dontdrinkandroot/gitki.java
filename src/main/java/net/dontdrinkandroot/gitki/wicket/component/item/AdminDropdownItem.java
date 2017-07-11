package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@AuthorizeAction(action = Action.RENDER, roles = Role.Constants.ADMIN)
public class AdminDropdownItem extends RepeatingDropdownItem<Void>
{
    public AdminDropdownItem(String id)
    {
        super(id, Model.of("Administration"));
    }

    @Override
    protected void populateItems(RepeatingView itemView)
    {
        itemView.add(new UserListPageItem(itemView.newChildId()));
        itemView.add(new ConfigurationPageItem(itemView.newChildId()));
    }
}
