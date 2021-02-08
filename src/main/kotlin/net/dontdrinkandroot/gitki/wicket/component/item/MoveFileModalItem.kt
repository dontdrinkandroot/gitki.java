package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.modal.MoveFileModal
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass
import net.dontdrinkandroot.wicket.bootstrap.event.CreateAndOpenModalRequest
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.event.Broadcast
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

@Render(Role.COMMITTER)
class MoveFileModalItem(id: String, private val pathModel: IModel<FilePath>) :
    AjaxLinkItem<Any>(id, Model.of("Move File")) {

    override fun onClick(target: AjaxRequestTarget) {
        send(
            this.page,
            Broadcast.DEPTH,
            CreateAndOpenModalRequest(target, MoveFileModal::class.java, pathModel)
        )
    }

    init {
        setPrependIcon(FontAwesomeIconClass.ARROWS_H.createIcon().setFixedWidth(true))
    }
}