package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel
import org.apache.wicket.model.IModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.TemporalAccessor

class TemporalAccessorStringModel constructor(
    parent: IModel<out TemporalAccessor>,
    private val formatStyle: FormatStyle = FormatStyle.MEDIUM
) : AbstractChainedReadonlyModel<TemporalAccessor, String>(parent) {

    override fun getObject(): String? {
        val temporalAccessor = this.parentObject ?: return null
        val formatter = DateTimeFormatter.ofLocalizedDateTime(formatStyle)
            .withLocale(getGitkiSession().locale)
            .withZone(getGitkiSession().zoneId)
        return formatter.format(temporalAccessor)
    }
}