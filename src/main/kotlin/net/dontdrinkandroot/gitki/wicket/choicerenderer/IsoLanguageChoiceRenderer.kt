package net.dontdrinkandroot.gitki.wicket.choicerenderer

import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel
import java.util.*

class IsoLanguageChoiceRenderer : IChoiceRenderer<String> {

    override fun getDisplayValue(language: String): Any = Locale(language).let { it.getDisplayLanguage(it) }

    override fun getIdValue(language: String, index: Int): String = language

    override fun getObject(id: String, choices: IModel<out MutableList<out String>>): String = id
}