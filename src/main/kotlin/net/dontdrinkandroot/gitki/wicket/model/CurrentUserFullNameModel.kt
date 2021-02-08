package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import org.apache.wicket.model.IModel

class CurrentUserFullNameModel : IModel<String> {

    override fun getObject(): String {
        val user = getGitkiSession().user ?: return "n/a"
        return user.fullName
    }
}