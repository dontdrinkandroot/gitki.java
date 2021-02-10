package net.dontdrinkandroot.gitki.wicket.page.file.view

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.component.FileActionsDropdownButton
import net.dontdrinkandroot.gitki.wicket.component.button.DownloadButton
import net.dontdrinkandroot.gitki.wicket.event.FileDeletedEvent
import net.dontdrinkandroot.gitki.wicket.event.FileMovedEvent
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import org.apache.wicket.event.IEvent
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.mapper.parameter.PageParameters

@Instantiate(value = Role.WATCHER, allowAnonymousIfConfigured = true)
open class ViewPage : FilePage {

    constructor(parameters: PageParameters?) : super(parameters!!) {
        if (!gitService.exists(this.modelObject)) {
            throw AbortWithHttpErrorCodeException(404)
        }
    }

    constructor(model: IModel<FilePath>) : super(model) {
        if (!gitService.exists(model.getObject()!!)) {
            throw AbortWithHttpErrorCodeException(404)
        }
    }

    override fun populatePrimaryButtons(view: RepeatingView) {
        super.populatePrimaryButtons(view)
        view.add(DownloadButton(view.newChildId(), this.model))
        view.add(FileActionsDropdownButton(view.newChildId(), this.model))
    }

    override fun onEvent(event: IEvent<*>) {
        super.onEvent(event)
        val payload = event.payload
        if (payload is FileDeletedEvent) {
            val directoryPath = gitService.findExistingDirectoryPath(payload.filePath)
            this.setResponsePage(DirectoryPage(Model.of(directoryPath)))
        }
        if (payload is FileMovedEvent) {
            this.setResponsePage(SimpleViewPage::class.java, PageParameterUtils.from(payload.targetPath))
        }
    }
}