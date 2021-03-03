package net.dontdrinkandroot.gitki.wicket.component.card.index

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.model.FilePathStringContentModel
import net.dontdrinkandroot.wicket.kmodel.KModel
import org.apache.wicket.Component
import org.apache.wicket.markup.html.basic.Label

class TextIndexFileCard(id: String, model: KModel<FilePath>) : IndexFileCard(id, model) {

    override fun createBody(id: String): Component {
        return Label(id, FilePathStringContentModel(this.model))
    }
}