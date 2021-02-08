package net.dontdrinkandroot.gitki.wicket.model.user

import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.wicket.model.AbstractChainedModel
import org.apache.wicket.model.IModel

class UserFirstNameModel(parent: IModel<out User>) : AbstractChainedModel<User, String>(parent) {

    override fun getObject(): String {
        return this.parentObject!!.firstName!!
    }

    override fun setObject(`object`: String) {
        this.parentObject!!.firstName = `object`
    }
}