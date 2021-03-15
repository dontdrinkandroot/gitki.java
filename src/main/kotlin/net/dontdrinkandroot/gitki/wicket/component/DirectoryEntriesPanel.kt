package net.dontdrinkandroot.gitki.wicket.component

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.GitkiPath
import net.dontdrinkandroot.gitki.wicket.component.listitem.DirectoryListItem
import net.dontdrinkandroot.gitki.wicket.component.listitem.FileListItem
import net.dontdrinkandroot.wicket.bootstrap.component.card.PlainCard
import net.dontdrinkandroot.wicket.bootstrap.component.list.listGroup
import org.apache.wicket.Component
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

class DirectoryEntriesPanel(id: String, model: IModel<List<GitkiPath>>) : PlainCard<List<GitkiPath>>(id, model) {

    override fun createAfterBody(id: String): Component = listGroup(id, this.model) { id, model ->
        val path = model.getObject()
        when {
            path.directoryPath -> {
                val pathModel: IModel<DirectoryPath> = Model(path as DirectoryPath)
                DirectoryListItem(id, pathModel)
            }
            else -> {
                val pathModel: IModel<FilePath> = Model(path as FilePath)
                FileListItem(id, pathModel)
            }
        }
    }
}