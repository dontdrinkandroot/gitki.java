package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.modal.RemoveFileModal
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass
import net.dontdrinkandroot.wicket.bootstrap.event.CreateAndOpenModalRequest
import net.dontdrinkandroot.wicket.model.model
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.event.Broadcast
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

@Render(Role.COMMITTER)
class RemoveFileModalItem(id: String, private val pathModel: IModel<FilePath>) :
    AjaxLinkItem<Void>(
        id,
        labelModel = Model.of("Remove File"),
        prependIconModel = FontAwesomeIconClass.TRASH_O.createIcon().setFixedWidth(true).model()
    ) {

    override fun onClick(target: AjaxRequestTarget?) {
        send(
            this.page,
            Broadcast.DEPTH,
            CreateAndOpenModalRequest(target, RemoveFileModal::class.java, pathModel)
        )
    }
}