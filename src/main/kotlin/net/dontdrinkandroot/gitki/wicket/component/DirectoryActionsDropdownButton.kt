package net.dontdrinkandroot.gitki.wicket.component

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.item.CreateDirectoryModalItem
import net.dontdrinkandroot.gitki.wicket.component.item.CreateFileModalItem
import net.dontdrinkandroot.gitki.wicket.component.item.MoveDirectoryModalItem
import net.dontdrinkandroot.gitki.wicket.component.item.UploadFilesModalItem
import net.dontdrinkandroot.gitki.wicket.security.Enable
import net.dontdrinkandroot.wicket.bootstrap.component.button.DropdownButton
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel

@Enable(Role.COMMITTER)
class DirectoryActionsDropdownButton(id: String, model: IModel<DirectoryPath>) :
    DropdownButton<DirectoryPath>(id, model, null) {

    override fun populateItems(itemView: RepeatingView) {
        itemView.add(CreateFileModalItem(itemView.newChildId(), this.model))
        itemView.add(CreateDirectoryModalItem(itemView.newChildId(), this.model))
        itemView.add(UploadFilesModalItem(itemView.newChildId(), this.model))
        itemView.add(MoveDirectoryModalItem(itemView.newChildId(), this.model))
    }

    init {
        setDropdownAlignment(DropdownAlignment.RIGHT)
    }
}