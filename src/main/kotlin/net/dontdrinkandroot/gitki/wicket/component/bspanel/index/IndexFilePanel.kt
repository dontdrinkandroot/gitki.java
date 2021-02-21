package net.dontdrinkandroot.gitki.wicket.component.bspanel.index

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel
import net.dontdrinkandroot.wicket.bootstrap.component.card.PlainCard
import net.dontdrinkandroot.wicket.component.basic.Heading
import org.apache.wicket.model.IModel

abstract class IndexFilePanel(id: String, model: IModel<FilePath>) : PlainCard<FilePath>(id, model) {

    override fun createHeader(id: String) = Heading(id, AbstractPathNameModel(this.model), Heading.Level.H2)
}