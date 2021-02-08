package net.dontdrinkandroot.gitki.wicket.model.lockinfo

import net.dontdrinkandroot.gitki.model.GitUser
import net.dontdrinkandroot.gitki.model.LockInfo
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel
import org.apache.wicket.model.IModel

class LockInfoUserModel(parent: IModel<out LockInfo>) : AbstractChainedReadonlyModel<LockInfo, GitUser>(parent) {

    override fun getObject(): GitUser {
        return this.parentObject!!.user
    }
}