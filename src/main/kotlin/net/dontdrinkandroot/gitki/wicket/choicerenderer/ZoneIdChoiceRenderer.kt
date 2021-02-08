package net.dontdrinkandroot.gitki.wicket.choicerenderer

import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel
import java.time.LocalDateTime
import java.time.ZoneId

class ZoneIdChoiceRenderer : IChoiceRenderer<String> {

    override fun getDisplayValue(`object`: String): Any {
        val zoneId = ZoneId.of(`object`)
        return String.format("%s (%s)", zoneId.id, zoneId.rules.getOffset(LocalDateTime.now()).id)
    }

    override fun getIdValue(`object`: String, index: Int): String {
        return `object`
    }

    override fun getObject(id: String, choices: IModel<out MutableList<out String>?>): String {
        return id
    }
}