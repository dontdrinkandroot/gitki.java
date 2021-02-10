package net.dontdrinkandroot.gitki.wicket.util

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.FilePath
import org.apache.wicket.request.mapper.parameter.PageParameters

object PageParameterUtils {

    @JvmOverloads
    fun from(path: DirectoryPath, pageParameters: PageParameters = PageParameters()): PageParameters {
        val segmentNames: List<String?> = path.segmentNames
        val segmentCount = segmentNames.size
        for (i in 0 until segmentCount) {
            pageParameters[i] = segmentNames[i]
        }
        pageParameters[segmentCount] = ""
        return pageParameters
    }

    @JvmOverloads
    fun from(path: FilePath, pageParameters: PageParameters = PageParameters()): PageParameters {
        val segmentNames: List<String?> = path.segmentNames
        val segmentCount = segmentNames.size
        for (i in 0 until segmentCount) {
            pageParameters[i] = segmentNames[i]
        }
        return pageParameters
    }

    fun toFilePath(parameters: PageParameters): FilePath {
        var directoryPath = DirectoryPath()
        val indexedCount = parameters.indexedCount
        for (i in 0 until indexedCount - 1) {
            val name = parameters[i].toString()
            directoryPath = directoryPath.appendDirectoryName(name)
        }
        return directoryPath.appendFileName(parameters[indexedCount - 1].toString())
    }

    fun toDirectoryPath(parameters: PageParameters?): DirectoryPath {
        var directoryPath = DirectoryPath()
        if (null == parameters || parameters.indexedCount == 1 && parameters[0].toString().isEmpty())
            return directoryPath
        val indexedCount = parameters.indexedCount
        for (i in 0 until indexedCount - 1) {
            val name = parameters[i].toString()
            directoryPath = directoryPath.appendDirectoryName(name)
        }
        return directoryPath
    }
}