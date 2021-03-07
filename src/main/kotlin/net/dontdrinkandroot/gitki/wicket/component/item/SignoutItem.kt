package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.wicket.getGitkiApplication
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.model.StringResourceModel

class SignoutItem(id: String) : AjaxLinkItem<Void?>(id, label = StringResourceModel("gitki.signout")) {

    override fun onClick(target: AjaxRequestTarget?) {
        getGitkiSession().invalidate()
        this.setResponsePage(getGitkiApplication().homePage)
    }
}