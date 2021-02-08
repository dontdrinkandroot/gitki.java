package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.modal.CreateFileModal
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass
import net.dontdrinkandroot.wicket.bootstrap.event.CreateAndOpenModalRequest
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.event.Broadcast
import org.apache.wicket.model.IModel
import org.apache.wicket.model.StringResourceModel

/**
 * @author Philip Washington Sorst <philip></philip>@sorst.net>
 */
@Render(Role.COMMITTER)
class CreateFileModalItem(id: String, private val pathModel: IModel<DirectoryPath>) :
    AjaxLinkItem<Any?>(id, StringResourceModel("gitki.file.create")) {

    override fun onClick(target: AjaxRequestTarget) {
        send(
            this.page,
            Broadcast.DEPTH,
            CreateAndOpenModalRequest(target, CreateFileModal::class.java, pathModel)
        )
    }

    init {
        setPrependIcon(FontAwesomeIconClass.PLUS.createIcon().setFixedWidth(true))
    }
}