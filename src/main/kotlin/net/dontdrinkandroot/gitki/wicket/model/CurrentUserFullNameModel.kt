package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.wicket.getCurrentUser
import org.apache.wicket.model.IModel

class CurrentUserFullNameModel : IModel<String> {

    override fun getObject(): String {
        val user = getCurrentUser() ?: return "n/a"
        return user.fullName
    }
}