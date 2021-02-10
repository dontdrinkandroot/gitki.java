package net.dontdrinkandroot.gitki.model

import org.apache.commons.lang3.builder.CompareToBuilder
import java.io.Serializable
import java.util.*
import java.nio.file.Path as NioPath

// TODO: Rename
interface GitkiPath : Comparable<GitkiPath>, Serializable {

    val root: Boolean

    val parent: DirectoryPath?

    val directoryPath: Boolean

    val isFilePath: Boolean
        get() = !directoryPath

    val name: String?

    val absoluteString: String
        get() = AbstractPath.SEPARATOR + this.toString()

    /** TODO: This should not be a mutable list */
    val segments: MutableList<GitkiPath>
        get() {
            if (root) {
                val paths: MutableList<GitkiPath> = ArrayList()
                paths.add(this)
                return paths
            }
            val paths = parent!!.segments
            paths.add(this)
            return paths
        }
    val nioPath: NioPath

    override fun compareTo(other: GitkiPath): Int {
        val compareToBuilder = CompareToBuilder()
        compareToBuilder.append(other.directoryPath, directoryPath)
        compareToBuilder.append(name, other.name)
        return compareToBuilder.toComparison()
    }
}