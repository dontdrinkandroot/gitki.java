package net.dontdrinkandroot.gitki.model

import java.util.*

abstract class AbstractPath : GitkiPath {

    override var name: String? = null
        protected set(value) {
            require(null != value && value.isNotEmpty()) { "Name must not be empty" }
            field = value
        }

    override var parent: DirectoryPath? = null
        protected set

    val segmentNames: MutableList<String?>
        get() {
            if (root) return ArrayList()
            val segmentNames = parent!!.segmentNames
            segmentNames.add(name)
            return segmentNames
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false
        val that = other as AbstractPath
        if (if (name != null) name != that.name else that.name != null) return false
        return if (parent != null) parent == that.parent else that.parent == null
    }

    override fun hashCode(): Int {
        var result = if (name != null) name.hashCode() else 0
        result = 31 * result + if (parent != null) parent.hashCode() else 0
        return result
    }

    companion object {

        const val SEPARATOR = "/"
    }
}