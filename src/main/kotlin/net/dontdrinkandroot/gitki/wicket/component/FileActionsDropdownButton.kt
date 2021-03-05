package net.dontdrinkandroot.gitki.wicket.component

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.item.EditFileItem
import net.dontdrinkandroot.gitki.wicket.component.item.MoveFileModalItem
import net.dontdrinkandroot.gitki.wicket.component.item.RemoveFileModalItem
import net.dontdrinkandroot.gitki.wicket.security.Enable
import net.dontdrinkandroot.wicket.bootstrap.component.button.DropdownButton
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment
import net.dontdrinkandroot.wicket.css.CssClass
import net.dontdrinkandroot.wicket.kmodel.model
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass as Fa5IconClass

@Enable(Role.COMMITTER)
class FileActionsDropdownButton(
    id: String, model: IModel<FilePath>,
    buttonStyleModel: IModel<ButtonStyle> = Model(null),
    buttonSize: ButtonSize? = null,
    prependIconModel: IModel<CssClass> = model(Fa5IconClass.ELLIPSIS_V.createIcon(fixedWidth = true)),
    appendIconModel: IModel<CssClass> = Model(null),
    dropdownAlignment: DropdownAlignment? = null
) : DropdownButton<FilePath>(
    id,
    model,
    null,
    buttonStyleModel,
    Model(buttonSize),
    prependIconModel,
    appendIconModel,
    Model(dropdownAlignment)
) {

    override fun populateItems(itemView: RepeatingView) {
        itemView.add(EditFileItem(itemView.newChildId(), this.model))
        itemView.add(MoveFileModalItem(itemView.newChildId(), this.model))
        itemView.add(RemoveFileModalItem(itemView.newChildId(), this.model))
    }
}