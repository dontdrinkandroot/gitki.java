package net.dontdrinkandroot.gitki.service.requestmapping

import net.dontdrinkandroot.gitki.wicket.component.bspanel.index.IndexFilePanel
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage

interface RequestMappingRegistry {

    fun setViewMappings(mappings: MutableMap<String, Class<out ViewPage>>)
    fun setEditMappings(mappings: MutableMap<String, Class<out EditPage>>)
    fun setIndexFileMappings(mappings: Map<String, Class<out IndexFilePanel>>)
    fun addViewMapping(extension: String, pageClass: Class<out ViewPage>)
    fun addEditMapping(extension: String, pageClass: Class<out EditPage>)
    fun resolveViewMapping(extension: String): Class<out ViewPage>?
    fun resolveEditMapping(extension: String): Class<out EditPage>?
    fun resolveIndexFilePanel(extension: String): Class<out IndexFilePanel>?
}