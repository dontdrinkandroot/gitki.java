package net.dontdrinkandroot.gitki.wicket.resource

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import org.apache.wicket.Application
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.request.cycle.RequestCycle
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.resource.AbstractResource
import org.apache.wicket.request.resource.IResource
import org.apache.wicket.request.resource.PartWriterCallback
import org.apache.wicket.spring.injection.annot.SpringBean
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.time.Duration

@Instantiate(value = Role.WATCHER, allowAnonymousIfConfigured = true)
class RawResource : AbstractResource() {

    @SpringBean
    private lateinit var gitService: GitService

    override fun newResourceResponse(attributes: IResource.Attributes): ResourceResponse {
        val filePath = PageParameterUtils.toFilePath(attributes.parameters)
        val fileSystemPath = try {
            gitService.resolveAbsolutePath(filePath, true)
        } catch (e: FileNotFoundException) {
            throw AbortWithHttpErrorCodeException(404)
        }
        return try {
            val fileAttributes = Files.readAttributes(fileSystemPath, BasicFileAttributes::class.java)
            val size = fileAttributes.size()
            val resourceResponse = ResourceResponse()
            resourceResponse.contentType = getMimeType(fileSystemPath)
            resourceResponse.acceptRange = ContentRangeType.BYTES
            resourceResponse.contentLength = size
            resourceResponse.lastModified = fileAttributes.lastModifiedTime().toInstant()
            resourceResponse.cacheDuration = Duration.ofMillis(1)
            val cycle = RequestCycle.get()
            val startbyte = cycle.getMetaData(CONTENT_RANGE_STARTBYTE)
            val endbyte = cycle.getMetaData(CONTENT_RANGE_ENDBYTE)
            resourceResponse.writeCallback =
                PartWriterCallback(Files.newInputStream(fileSystemPath), size, startbyte, endbyte)
            resourceResponse
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        }
    }

    @Throws(IOException::class)
    protected fun getMimeType(path: Path): String? {
        var mimeType: String? = null
        if (Application.exists()) {
            mimeType = Application.get().getMimeType(path.fileName.toString())
        }
        if (mimeType == null) {
            mimeType = Files.probeContentType(path)
        }
        return mimeType
    }
}