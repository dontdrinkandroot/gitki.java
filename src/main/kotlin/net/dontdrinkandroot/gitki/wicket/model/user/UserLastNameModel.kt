package net.dontdrinkandroot.gitki.wicket.model.user

import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.wicket.model.AbstractChainedModel
import org.apache.wicket.model.IModel

class UserLastNameModel(parent: IModel<out User>) : AbstractChainedModel<User, String>(parent) {

    override fun getObject(): String {
        return this.parentObject!!.lastName!!
    }

    override fun setObject(`object`: String) {
        this.parentObject!!.lastName = `object`
    }
}