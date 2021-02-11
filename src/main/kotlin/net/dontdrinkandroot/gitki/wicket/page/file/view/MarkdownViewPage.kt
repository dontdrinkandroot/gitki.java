package net.dontdrinkandroot.gitki.wicket.page.file.view

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.service.markdown.MarkdownService
import net.dontdrinkandroot.gitki.wicket.component.button.EditButton
import net.dontdrinkandroot.gitki.wicket.component.button.PrintButton
import net.dontdrinkandroot.gitki.wicket.head.HighlightInitHeaderItem
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean
import java.io.FileNotFoundException
import java.io.IOException

class MarkdownViewPage : ViewPage {

    @SpringBean
    private lateinit var markdownService: MarkdownService

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<FilePath>) : super(model)

    override fun onInitialize() {
        super.onInitialize()
        try {
            val renderedMarkdown = markdownService.parseToHtml(gitService.getContentAsString(this.modelObject))
            this.add(Label("content", renderedMarkdown).setEscapeModelStrings(false))
        } catch (e: FileNotFoundException) {
            throw AbortWithHttpErrorCodeException(404)
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun populatePrimaryButtons(view: RepeatingView) {
        view.add(EditButton(view.newChildId(), this.model))
        view.add(PrintButton(view.newChildId()))
        super.populatePrimaryButtons(view)
    }

    override fun renderHead(response: IHeaderResponse) {
        super.renderHead(response)
        response.render(HighlightInitHeaderItem())
    }
}