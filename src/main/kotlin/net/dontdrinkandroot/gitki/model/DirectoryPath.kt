package net.dontdrinkandroot.gitki.model

import java.nio.file.Paths
import java.nio.file.Path as NioPath

class DirectoryPath : AbstractPath {

    private constructor(name: String, directoryPath: DirectoryPath) {
        this.name = name
        parent = directoryPath
    }

    constructor()

    constructor(name: String) {
        this.name = name
        parent = DirectoryPath()
    }

    override val root: Boolean
        get() = null == parent

    override val directoryPath = true

    fun appendFileName(name: String) = FilePath(name, this)

    fun appendDirectoryName(name: String) = DirectoryPath(name, this)

    override fun toString(): String = if (null == parent) "" else parent.toString() + name + "/"

    override val nioPath: NioPath
        get() {
            if (root) return Paths.get("")
            val parentPath = parent!!.nioPath
            return parentPath.resolve(name)
        }

    companion object {

        @JvmStatic
        fun parse(pathString: String): DirectoryPath {
            require(pathString.endsWith(SEPARATOR)) { "Path string must end with $SEPARATOR" }
            val parts: List<String> = pathString.split(SEPARATOR).filter { it.isNotEmpty() }
            return from(parts)
        }

        fun from(parts: List<String>): DirectoryPath {
            val path = DirectoryPath(parts.last())
            if (parts.size > 1) {
                path.parent = from(parts.subList(0, parts.size - 1))
            }
            return path
        }

        @JvmStatic
        fun from(path: NioPath): DirectoryPath {
            if ("" == path.toString()) {
                return DirectoryPath()
            }
            val parent = path.parent
            return if (null != parent) {
                from(parent).appendDirectoryName(path.fileName.toString())
            } else DirectoryPath(path.fileName.toString())
        }
    }
}