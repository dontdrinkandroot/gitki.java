package net.dontdrinkandroot.gitki.wicket.column

import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.wicket.dropdown.OptionLinkDropdown
import net.dontdrinkandroot.gitki.wicket.page.admin.UserListPage
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.component.item.linkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import net.dontdrinkandroot.wicket.bootstrap.css.TextColor
import net.dontdrinkandroot.wicket.model.model
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel

class UserActionColumn : AbstractColumn<User, String>(Model.of("")) {

    override fun populateItem(cellItem: Item<ICellPopulator<User>>, componentId: String, rowModel: IModel<User>) {
        cellItem.add(OptionLinkDropdown(componentId) { itemView ->
            itemView.add(linkItem(
                itemView.newChildId(),
                rowModel,
                labelModel = StringResourceModel("gitki.edit"),
                prependIconModel = FontAwesome5IconClass.EDIT.createIcon().apply { fixedWidth = true }.model()
            ) {
                setResponsePage(UserEditPage(model))
            })
            itemView.add(linkItem(
                itemView.newChildId(),
                rowModel,
                labelModel = StringResourceModel("gitki.remove"),
                prependIconModel = FontAwesome5IconClass.TRASH.createIcon().apply { fixedWidth = true }.model(),
                linkBehaviors = listOf(CssClassAppender(TextColor.DANGER))
            ) {
                setResponsePage(UserListPage())
            })
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