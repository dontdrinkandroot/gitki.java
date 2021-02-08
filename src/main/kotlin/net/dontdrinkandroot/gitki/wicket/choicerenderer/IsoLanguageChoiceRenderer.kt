package net.dontdrinkandroot.gitki.wicket.choicerenderer

import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel
import java.util.*

class IsoLanguageChoiceRenderer : IChoiceRenderer<String> {

    override fun getDisplayValue(`object`: String): Any {
        val locale = Locale(`object`)
        return locale.getDisplayLanguage(locale)
    }

    override fun getIdValue(`object`: String, index: Int): String {
        return `object`
    }

    override fun getObject(id: String, choices: IModel<out MutableList<out String>?>): String {
        return id
    }
}