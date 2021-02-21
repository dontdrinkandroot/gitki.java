package net.dontdrinkandroot.gitki.wicket.component

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.component.listitem.FileListItem
import net.dontdrinkandroot.wicket.bootstrap.component.card.PlainCard
import net.dontdrinkandroot.wicket.bootstrap.component.list.ListGroup
import net.dontdrinkandroot.wicket.component.basic.Heading
import org.apache.wicket.Component
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

class FileListPanel(id: String, model: IModel<List<FilePath>>) : PlainCard<List<FilePath>>(id, model) {

    override fun createHeader(id: String) = Heading(id, Model("Files"), Heading.Level.H2)

    override fun createAfterBody(id: String): Component {
        return object : ListGroup<FilePath>(id, model) {
            override fun createListComponent(id: String, model: IModel<FilePath>): Component {
                return FileListItem(id, Model.of(model.getObject()))
            }
        }
    }
}