package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.modal.MoveDirectoryModal
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import net.dontdrinkandroot.wicket.bootstrap.event.CreateAndOpenModalRequest
import net.dontdrinkandroot.wicket.model.model
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.event.Broadcast
import org.apache.wicket.model.IModel
import org.apache.wicket.model.StringResourceModel

@Render(Role.COMMITTER)
class MoveDirectoryModalItem(id: String, private val pathModel: IModel<DirectoryPath>) :
    AjaxLinkItem<Void>(
        id,
        labelModel = StringResourceModel("gitki.move"),
        prependIconModel = FontAwesome5IconClass.ARROWS_ALT_H.createIcon().apply { fixedWidth = true }.model()
    ) {

    override fun onClick(target: AjaxRequestTarget?) {
        target?.let {
            send(
                this.page,
                Broadcast.DEPTH,
                CreateAndOpenModalRequest(it, MoveDirectoryModal::class, pathModel)
            )
        }
    }
}