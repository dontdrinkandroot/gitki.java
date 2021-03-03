package net.dontdrinkandroot.gitki.wicket.component.card.index

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel
import net.dontdrinkandroot.wicket.bootstrap.component.card.PlainCard
import net.dontdrinkandroot.wicket.component.basic.Heading
import net.dontdrinkandroot.wicket.kmodel.KModel
import net.dontdrinkandroot.wicket.kmodel.kModel

abstract class IndexFileCard(id: String, model: KModel<FilePath>) : PlainCard<FilePath>(id, model) {

    override fun createHeader(id: String) = Heading(id, AbstractPathNameModel(this.kModel), Heading.Level.H2)
}