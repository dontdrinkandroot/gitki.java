package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.wicket.model.AbstractChainedModel
import org.apache.wicket.model.IModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.TemporalAccessor

class TemporalAccessorStringModel constructor(
    parent: IModel<out TemporalAccessor?>,
    private val formatStyle: FormatStyle = FormatStyle.MEDIUM
) : AbstractChainedModel<TemporalAccessor, String>(parent) {

    override fun getValue(parentValue: TemporalAccessor?): String? = parentValue?.let {
        val formatter = DateTimeFormatter.ofLocalizedDateTime(formatStyle)
            .withLocale(getGitkiSession().locale)
            .withZone(getGitkiSession().zoneId)
        return formatter.format(parentValue)
    }
}