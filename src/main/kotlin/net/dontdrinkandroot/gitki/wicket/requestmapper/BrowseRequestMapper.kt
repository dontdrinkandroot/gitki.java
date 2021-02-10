package net.dontdrinkandroot.gitki.wicket.requestmapper

import net.dontdrinkandroot.gitki.service.requestmapping.RequestMappingRegistry
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage
import org.apache.commons.io.FilenameUtils
import org.apache.wicket.core.request.handler.ListenerRequestHandler
import org.apache.wicket.core.request.mapper.AbstractBookmarkableMapper
import org.apache.wicket.core.request.mapper.MapperUtils
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.request.IRequestHandler
import org.apache.wicket.request.Request
import org.apache.wicket.request.Url
import org.apache.wicket.request.mapper.info.ComponentInfo
import org.apache.wicket.request.mapper.info.PageComponentInfo
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean
import org.apache.wicket.util.string.StringValue

class BrowseRequestMapper : AbstractBookmarkableMapper() {

    @SpringBean
    private lateinit var requestMappingRegistry: RequestMappingRegistry

    override fun parseRequest(request: Request): UrlInfo? {
        val url = request.url
        val segments = url.segments
        if (segments.size < 1 || segments[0] != "browse") return null
        val pageComponentInfo = MapperUtils.getPageComponentInfo(request.url)
        val lastSegment = segments[segments.size - 1]

        /* Directory Page */
        if ("" == lastSegment) {
            val parameters = PageParameters()
            for (i in 1 until segments.size) parameters[i - 1] = segments[i]
            return UrlInfo(pageComponentInfo, DirectoryPage::class.java, parameters)
        }

        /* File Page */
        val parameters = PageParameters()
        for (i in 1 until segments.size) parameters[i - 1] = segments[i]
        val actionValue = url.getQueryParameterValue("action")
        if (!actionValue.isNull && !actionValue.isEmpty) parameters["action"] = actionValue.toString()
        return UrlInfo(
            pageComponentInfo,
            resolveFilePageClass(lastSegment, actionValue),
            parameters
        )
    }

    override fun buildUrl(info: UrlInfo): Url? = when {
        DirectoryPage::class.java.isAssignableFrom(info.pageClass) -> {
            val url = Url()
            url.segments.add("browse")
            encodePageComponentInfo(url, info.pageComponentInfo)
            val copy = PageParameters(info.pageParameters)
            if (copy.indexedCount == 0) copy[0] = ""
            encodePageParameters(url, copy, pageParametersEncoder)
        }
        FilePage::class.java.isAssignableFrom(info.pageClass) -> {
            val url = Url()
            url.segments.add("browse")
            encodePageComponentInfo(url, info.pageComponentInfo)
            val copy = PageParameters(info.pageParameters)
            encodePageParameters(url, copy, pageParametersEncoder)
        }
        else -> null
    }

    override fun mapHandler(requestHandler: IRequestHandler): Url? {
        var url = super.mapHandler(requestHandler)

        /* TODO: Recheck */
        if (url == null && requestHandler is ListenerRequestHandler && recreateMountedPagesAfterExpiry) {
            val page = requestHandler.page
            if (checkPageInstance(page)) {
                var renderCount: Int? = null
                if (requestHandler.includeRenderCount()) renderCount = requestHandler.renderCount
                val pageInfo = getPageInfo(requestHandler)
                val componentInfo = ComponentInfo(
                    renderCount,
                    requestHandler.componentPath,
                    requestHandler.behaviorIndex
                )
                val pageComponentInfo = PageComponentInfo(pageInfo, componentInfo)
                val parameters = PageParameters(page.pageParameters)
                val urlInfo =
                    UrlInfo(pageComponentInfo, page.javaClass, parameters.mergeWith(requestHandler.pageParameters))
                url = buildUrl(urlInfo)
            }
        }
        return url
    }

    val recreateMountedPagesAfterExpiry: Boolean
        get() = WebApplication.get().pageSettings.recreateBookmarkablePagesAfterExpiry

    protected fun resolveFilePageClass(fileName: String, actionValue: StringValue): Class<out FilePage> {
        val action = actionValue.toString("view")
        val extension = FilenameUtils.getExtension(fileName)
        val pageClass: Class<out FilePage>?
        when (action) {
            "view" -> {
                pageClass = requestMappingRegistry.resolveViewMapping(extension)
                if (null != pageClass) return pageClass
            }
            "edit" -> {
                pageClass = requestMappingRegistry.resolveEditMapping(extension)
                if (null != pageClass) return pageClass
            }
        }
        return SimpleViewPage::class.java
    }

    override fun pageMustHaveBeenCreatedBookmarkable(): Boolean = false
}