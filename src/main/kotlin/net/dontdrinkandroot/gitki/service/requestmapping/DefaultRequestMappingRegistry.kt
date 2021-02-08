package net.dontdrinkandroot.gitki.service.requestmapping

import net.dontdrinkandroot.gitki.wicket.component.bspanel.index.IndexFilePanel
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class DefaultRequestMappingRegistry @Inject constructor(
    @Value("#{gitkiConfigurationProperties.viewMappings}") private var viewMappings: MutableMap<String, Class<out ViewPage>>,
    @Value("#{gitkiConfigurationProperties.editMappings}") private var editMappings: MutableMap<String, Class<out EditPage>>,
    @Value("#{gitkiConfigurationProperties.indexFileMappings}") private var indexFileMappings: Map<String, Class<out IndexFilePanel>>
) : RequestMappingRegistry {

    override fun setViewMappings(mappings: MutableMap<String, Class<out ViewPage>>) {
        viewMappings = mappings
    }

    override fun setEditMappings(mappings: MutableMap<String, Class<out EditPage>>) {
        editMappings = mappings
    }

    override fun setIndexFileMappings(mappings: Map<String, Class<out IndexFilePanel>>) {
        indexFileMappings = mappings
    }

    override fun addViewMapping(extension: String, pageClass: Class<out ViewPage>) {
        viewMappings.put(extension, pageClass)
    }

    override fun addEditMapping(extension: String, pageClass: Class<out EditPage>) {
        editMappings.put(extension, pageClass)
    }

    override fun resolveViewMapping(extension: String): Class<out ViewPage>? =
        if (!viewMappings.containsKey(extension)) null else viewMappings[extension]

    override fun resolveEditMapping(extension: String): Class<out EditPage>? =
        if (!editMappings.containsKey(extension)) null else editMappings[extension]

    override fun resolveIndexFilePanel(extension: String): Class<out IndexFilePanel>? =
        if (!indexFileMappings.containsKey(extension)) null else indexFileMappings[extension]
}