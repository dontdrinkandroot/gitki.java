package net.dontdrinkandroot.gitki.wicket.column;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.Button;
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserActionColumn extends AbstractColumn<User, String>
{
    public UserActionColumn()
    {
        super(Model.of("Actions"));
    }

    @Override
    public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> rowModel)
    {
        Button<User> editButton = new Button<User>(componentId, rowModel)
        {
            @Override
            public void onClick()
            {
                this.setResponsePage(new UserEditPage(this.getModel()));
            }
        };
        editButton.add(new IconBehavior(FontAwesomeIconClass.PENCIL.createIcon().setFixedWidth(true)));
        editButton.setBody(Model.of(""));
        editButton.setButtonSize(ButtonSize.EXTRA_SMALL);
        cellItem.add(editButton);
    }
}
