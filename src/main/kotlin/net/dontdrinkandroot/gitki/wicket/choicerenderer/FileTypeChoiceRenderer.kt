package net.dontdrinkandroot.gitki.wicket.choicerenderer

import net.dontdrinkandroot.gitki.model.FileType
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel

class FileTypeChoiceRenderer : IChoiceRenderer<FileType> {

    override fun getDisplayValue(fileType: FileType): Any {
        val name = fileType.name
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase()
    }

    override fun getIdValue(fileType: FileType, index: Int): String {
        return fileType.name
    }

    override fun getObject(id: String, choices: IModel<out MutableList<out FileType>?>): FileType {
        return FileType.valueOf(id)
    }
}