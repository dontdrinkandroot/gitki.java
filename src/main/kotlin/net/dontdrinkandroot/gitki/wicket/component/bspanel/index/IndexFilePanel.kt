package net.dontdrinkandroot.gitki.wicket.component.bspanel.index

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PanelHeading
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PlainPanel
import net.dontdrinkandroot.wicket.component.basic.Heading
import org.apache.wicket.Component
import org.apache.wicket.model.IModel

abstract class IndexFilePanel(id: String, model: IModel<FilePath>) : PlainPanel<FilePath>(id, model) {

    override fun createHeading(id: String): Component {
        return PanelHeading(id, AbstractPathNameModel(this.model), Heading.Level.H2)
    }
}