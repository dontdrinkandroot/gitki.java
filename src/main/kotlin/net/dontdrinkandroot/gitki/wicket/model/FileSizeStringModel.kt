package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.wicket.model.AbstractChainedModel
import org.apache.wicket.model.IModel
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

class FileSizeStringModel(parent: IModel<out Long>) : AbstractChainedModel<Long, String>(parent) {

    override fun getValue(parentValue: Long?): String? = parentValue?.let {
        if (it <= 0) return "0"
        val units = arrayOf("B", "kB", "MB", "GB", "TB")
        val digitGroups = (log10(it.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("###0.#").format(it / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
    }
}