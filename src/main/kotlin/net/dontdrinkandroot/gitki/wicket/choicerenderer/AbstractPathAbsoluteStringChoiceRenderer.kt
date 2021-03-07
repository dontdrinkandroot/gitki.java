package net.dontdrinkandroot.gitki.wicket.choicerenderer

import net.dontdrinkandroot.gitki.model.GitkiPath
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel

class AbstractPathAbsoluteStringChoiceRenderer<T : GitkiPath> : IChoiceRenderer<T> {

    override fun getDisplayValue(value: T): Any = value.absoluteString

    override fun getIdValue(value: T, index: Int): String = value.absoluteString

    override fun getObject(id: String, choices: IModel<out List<T>>): T? {
        choices.getObject().forEachIndexed { index, choice -> if (getIdValue(choice, index) == id) return choice }
        return null
    }
}