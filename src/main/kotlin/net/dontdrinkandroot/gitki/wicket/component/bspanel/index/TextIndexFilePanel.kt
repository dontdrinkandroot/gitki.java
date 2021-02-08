package net.dontdrinkandroot.gitki.wicket.component.bspanel.index

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.model.FilePathStringContentModel
import org.apache.wicket.Component
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel

class TextIndexFilePanel(id: String, model: IModel<FilePath>) : IndexFilePanel(id, model) {

    override fun createBody(id: String): Component {
        return Label(id, FilePathStringContentModel(this.model))
    }
}