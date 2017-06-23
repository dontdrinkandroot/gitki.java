package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.ADMIN)
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
