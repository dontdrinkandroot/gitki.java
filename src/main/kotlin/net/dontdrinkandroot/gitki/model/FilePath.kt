package net.dontdrinkandroot.gitki.model

import net.dontdrinkandroot.gitki.model.DirectoryPath.Companion.from
import org.apache.commons.io.FilenameUtils
import java.nio.file.Path as NioPath

class FilePath : AbstractPath {

    internal constructor(name: String, directoryPath: DirectoryPath) {
        this.name = name
        parent = directoryPath
    }

    constructor(name: String) {
        this.name = name
        parent = DirectoryPath()
    }

    override fun toString(): String = parent.toString() + name

    override val nioPath: NioPath
        get() {
            val parentPath = parent!!.nioPath
            return parentPath.resolve(name)
        }

    override val root: Boolean
        get() = false

    override val directoryPath = false

    val extension: String
        get() = FilenameUtils.getExtension(name)

    val nameWithoutExtension: String
        get() = FilenameUtils.removeExtension(name)

    companion object {

        @JvmStatic
        fun parse(pathString: String): FilePath {
            require(!pathString.endsWith(SEPARATOR)) { "pathString must not end with $SEPARATOR" }
            val parts: List<String> = pathString.split(SEPARATOR)
            val filePath = FilePath(parts.last())
            if (parts.size > 1) {
                filePath.parent = from(parts.subList(0, parts.size - 1))
            }
            return filePath
        }

        @JvmStatic
        fun from(path: NioPath): FilePath {
            var directoryPath = DirectoryPath()
            val parent = path.parent
            if (null != parent) {
                directoryPath = DirectoryPath.from(path.parent)
            }
            return directoryPath.appendFileName(path.fileName.toString())
        }
    }
}