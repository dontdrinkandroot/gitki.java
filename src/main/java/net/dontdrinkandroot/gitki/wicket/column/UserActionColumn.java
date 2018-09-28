package net.dontdrinkandroot.gitki.wicket.column;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.wicket.dropdown.OptionLinkDropdown;
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage;
import net.dontdrinkandroot.wicket.bootstrap.component.item.LinkItem;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserActionColumn extends AbstractColumn<User, String>
{
    public UserActionColumn()
    {
        super(Model.of(""));
    }

    @Override
    public void populateItem(Item<ICellPopulator<User>> cellItem, String componentId, IModel<User> rowModel)
    {
        cellItem.add(new OptionLinkDropdown(componentId)
        {
            @Override
            protected void populateItems(RepeatingView itemView)
            {
                LinkItem editItem =
                        new LinkItem<User>(itemView.newChildId(), new StringResourceModel("gitki.edit"), rowModel)
                        {
                            @Override
                            protected void onClick()
                            {
                                this.setResponsePage(new UserEditPage(this.getModel()));
                            }
                        };
                editItem.setPrependIcon(FontAwesome5IconClass.EDIT.createIcon().setFixedWidth(true));
                itemView.add(editItem);
            }
        });

        //        Button<User> editButton = new Button<User>(componentId, rowModel)
        //        {
        //            @Override
        //            public void onClick()
        //            {
        //                this.setResponsePage(new UserEditPage(this.getModel()));
        //            }
        //        };
        //        //editButton.add(new IconBehavior(FontAwesomeIconClass.PENCIL.createIcon().setFixedWidth(true)));
        //        editButton.add(new CssClassAppender(new StringCssClass("fas fa-edit")));
        //        editButton.setBody(Model.of(""));
        //        editButton.setButtonSize(ButtonSize.SMALL);
        //        cellItem.add(editButton);
    }
}
