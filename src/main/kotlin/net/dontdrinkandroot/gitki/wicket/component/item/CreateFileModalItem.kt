package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.modal.CreateFileModal
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem
import net.dontdrinkandroot.wicket.bootstrap.event.CreateAndOpenModalRequest
import net.dontdrinkandroot.wicket.kmodel.model
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.event.Broadcast
import org.apache.wicket.model.IModel
import org.apache.wicket.model.StringResourceModel
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass as Fa5IconClass

@Render(Role.COMMITTER)
class CreateFileModalItem(id: String, private val pathModel: IModel<DirectoryPath>) :
    AjaxLinkItem<Any>(
        id,
        labelModel = StringResourceModel("gitki.file.create"),
        prependIconModel = model(Fa5IconClass.PLUS.createIcon(fixedWidth = true))
    ) {

    override fun onClick(target: AjaxRequestTarget?) {
        target?.let {
            send(
                this.page,
                Broadcast.DEPTH,
                CreateAndOpenModalRequest(target, CreateFileModal::class, pathModel)
            )
        }
    }
}