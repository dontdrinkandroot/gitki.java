package net.dontdrinkandroot.gitki.wicket.column

import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.wicket.dropdown.OptionLinkDropdown
import net.dontdrinkandroot.gitki.wicket.page.admin.UserListPage
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage
import net.dontdrinkandroot.wicket.behavior.appendCssClass
import net.dontdrinkandroot.wicket.bootstrap.behavior.icon
import net.dontdrinkandroot.wicket.bootstrap.component.item.link
import net.dontdrinkandroot.wicket.bootstrap.css.TextColor
import net.dontdrinkandroot.wicket.model.localize
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass as Fa5Icon

class UserActionColumn : AbstractColumn<User, String>(Model.of("")) {

    override fun populateItem(cellItem: Item<ICellPopulator<User>>, componentId: String, rowModel: IModel<User>) {
        cellItem.add(OptionLinkDropdown(componentId) {
            link(localize("gitki.edit"), rowModel, icon(Fa5Icon.EDIT.createIcon(fixedWidth = true))) {
                setResponsePage(UserEditPage(model))
            }
            link(
                localize("gitki.remove"),
                rowModel,
                icon(Fa5Icon.TRASH.createIcon(fixedWidth = true)),
                appendCssClass(TextColor.DANGER)
            ) {
                setResponsePage(UserListPage())
            }
        })

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