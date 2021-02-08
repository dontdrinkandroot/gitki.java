package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.model.GitkiPath
import net.dontdrinkandroot.wicket.model.AbstractChainedModel
import org.apache.wicket.model.IModel

class AbstractPathNameModel(parent: IModel<GitkiPath>) : AbstractChainedModel<GitkiPath, String>(parent) {

    override fun getObject(): String {
        val path: GitkiPath = this.getParentModelObject()
        return if (path.root) "/" else path.name!!
    }
}