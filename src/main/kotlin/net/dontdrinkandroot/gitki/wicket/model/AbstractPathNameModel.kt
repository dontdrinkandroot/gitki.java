package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.model.GitkiPath
import net.dontdrinkandroot.wicket.model.AbstractChainedModel
import org.apache.wicket.model.IModel

class AbstractPathNameModel(parent: IModel<out GitkiPath>) : AbstractChainedModel<GitkiPath, String>(parent) {

    override fun getValue(parentValue: GitkiPath?): String? = parentValue?.let {
        return if (it.root) "/" else it.name!!
    }
}