package net.dontdrinkandroot.gitki.wicket.model.lockinfo

import net.dontdrinkandroot.gitki.model.LockInfo
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel
import org.apache.wicket.model.IModel
import java.time.Instant

class LockInfoExpiryModel(parent: IModel<out LockInfo>) : AbstractChainedReadonlyModel<LockInfo, Instant>(parent) {

    override fun getObject(): Instant {
        return this.parentObject!!.expiry
    }
}