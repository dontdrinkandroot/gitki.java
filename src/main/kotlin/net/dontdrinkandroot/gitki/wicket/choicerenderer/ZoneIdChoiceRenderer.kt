package net.dontdrinkandroot.gitki.wicket.choicerenderer

import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel
import java.time.LocalDateTime
import java.time.ZoneId

class ZoneIdChoiceRenderer : IChoiceRenderer<String> {

    override fun getDisplayValue(zoneId: String): Any = ZoneId.of(zoneId).let {
        String.format("%s (%s)", it.id, it.rules.getOffset(LocalDateTime.now()).id)
    }

    override fun getIdValue(zoneId: String, index: Int): String = zoneId

    override fun getObject(id: String, choices: IModel<out MutableList<out String>>): String = id
}