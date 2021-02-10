package net.dontdrinkandroot.gitki.wicket.page.directory

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.requestmapping.RequestMappingRegistry
import net.dontdrinkandroot.gitki.service.wiki.WikiService
import net.dontdrinkandroot.gitki.wicket.component.DirectoryActionsDropdownButton
import net.dontdrinkandroot.gitki.wicket.component.DirectoryEntriesPanel
import net.dontdrinkandroot.gitki.wicket.event.DirectoryMovedEvent
import net.dontdrinkandroot.gitki.wicket.event.FileDeletedEvent
import net.dontdrinkandroot.gitki.wicket.event.FileMovedEvent
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel
import net.dontdrinkandroot.gitki.wicket.model.DirectoryPathEntriesModel
import net.dontdrinkandroot.gitki.wicket.page.BrowsePage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass
import org.apache.wicket.Component
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.event.IEvent
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean
import java.lang.reflect.InvocationTargetException

@Instantiate(value = Role.WATCHER, allowAnonymousIfConfigured = true)
class DirectoryPage : BrowsePage<DirectoryPath> {

    @SpringBean
    private lateinit var requestMappingRegistry: RequestMappingRegistry

    @SpringBean
    private lateinit var wikiService: WikiService

    private var entriesPanel: DirectoryEntriesPanel? = null

    constructor(model: IModel<DirectoryPath>) : super(model) {
        PageParameterUtils.from(model.getObject(), pageParameters)
    }

    constructor(parameters: PageParameters) : super(parameters) {
        val path = PageParameterUtils.toDirectoryPath(parameters)
        this.model = Model.of(path)
    }

    override fun onInitialize() {
        super.onInitialize()
        this.add(Label("heading", AbstractPathNameModel(this.model)))
        entriesPanel = DirectoryEntriesPanel("entries", DirectoryPathEntriesModel(this.model))
        entriesPanel!!.outputMarkupId = true
        this.add(entriesPanel)
        val indexFileComponent = createIndexFileComponent("indexFile")
        this.add(indexFileComponent)
    }

    private fun createIndexFileComponent(id: String): Component {
        val indexFilePath = wikiService.resolveIndexFile(this.modelObject!!)
            ?: return WebMarkupContainer(id).setVisible(false)
        val clazz = requestMappingRegistry.resolveIndexFilePanel(indexFilePath.extension)
            ?: return WebMarkupContainer(id).setVisible(false)
        val indexFilePathModel: IModel<FilePath> = Model.of(indexFilePath)
        return try {
            val constructor = clazz.getDeclaredConstructor(String::class.java, IModel::class.java)
            constructor.newInstance(id, indexFilePathModel)
        } catch (e: NoSuchMethodException) {
            throw WicketRuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw WicketRuntimeException(e)
        } catch (e: InstantiationException) {
            throw WicketRuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun populatePrimaryButtons(view: RepeatingView) {
        super.populatePrimaryButtons(view)
        val directoryActionsButton = DirectoryActionsDropdownButton(view.newChildId(), this.model)
        directoryActionsButton.iconBehavior.appendIcon =
            FontAwesomeIconClass.ELLIPSIS_V.createIcon().setFixedWidth(true)
        view.add(directoryActionsButton)
    }

    override fun onEvent(event: IEvent<*>) {
        super.onEvent(event)
        val payload = event.payload
        if (payload is FileDeletedEvent) {
            val directoryPath = gitService.findExistingDirectoryPath(payload.filePath)
            if (directoryPath == this.modelObject) {
                payload.target!!.add(entriesPanel)
                return
            }
            this.setResponsePage(DirectoryPage(Model.of(directoryPath)))
            return
        }
        if (payload is FileMovedEvent) {
            payload.target!!.add(entriesPanel)
            return
        }
        if (payload is DirectoryMovedEvent) {
            if (payload.directoryPath.equals(this.modelObject)) {
                this.setResponsePage(DirectoryPage(Model.of(payload.targetPath)))
            } else {
                payload.target!!.add(entriesPanel)
            }
        }
    }
}