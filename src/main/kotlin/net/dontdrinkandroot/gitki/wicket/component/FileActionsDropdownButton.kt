package net.dontdrinkandroot.gitki.wicket.component

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.item.EditFileItem
import net.dontdrinkandroot.gitki.wicket.component.item.MoveFileModalItem
import net.dontdrinkandroot.gitki.wicket.component.item.RemoveFileModalItem
import net.dontdrinkandroot.gitki.wicket.security.Enable
import net.dontdrinkandroot.wicket.bootstrap.component.button.DropdownButton
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel

@Enable(Role.COMMITTER)
class FileActionsDropdownButton(id: String, model: IModel<FilePath>) : DropdownButton<FilePath>(id, model, null) {

    override fun populateItems(itemView: RepeatingView) {
        itemView.add(EditFileItem(itemView.newChildId(), this.model))
        itemView.add(MoveFileModalItem(itemView.newChildId(), this.model))
        itemView.add(RemoveFileModalItem(itemView.newChildId(), this.model))
    }

    init {
        setDropdownAlignment(DropdownAlignment.RIGHT)
    }
}