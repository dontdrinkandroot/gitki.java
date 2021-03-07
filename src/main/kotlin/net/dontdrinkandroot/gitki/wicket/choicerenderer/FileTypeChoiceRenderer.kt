package net.dontdrinkandroot.gitki.wicket.choicerenderer

import net.dontdrinkandroot.gitki.model.FileType
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel

class FileTypeChoiceRenderer : IChoiceRenderer<FileType> {

    override fun getDisplayValue(fileType: FileType): String = fileType.name.capitalize()

    override fun getIdValue(fileType: FileType, index: Int): String = fileType.name

    override fun getObject(id: String, choices: IModel<out MutableList<out FileType>>) = FileType.valueOf(id)
}