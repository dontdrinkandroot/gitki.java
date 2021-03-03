package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.model.GitkiPath
import net.dontdrinkandroot.wicket.kmodel.KModel

class AbstractPathNameModel(private val parentModel: KModel<out GitkiPath>) : KModel<String> {

    override val value: String
        get() = parentModel.value.let { return if (it.root) "/" else it.name!! }

    override fun detach() {
        parentModel.detach()
    }
}