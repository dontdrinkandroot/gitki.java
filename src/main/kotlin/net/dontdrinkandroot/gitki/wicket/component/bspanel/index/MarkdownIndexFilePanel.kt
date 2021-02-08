package net.dontdrinkandroot.gitki.wicket.component.bspanel.index

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.service.markdown.MarkdownService
import net.dontdrinkandroot.gitki.wicket.headeritem.HighlightInitHeaderItem
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import org.apache.wicket.Component
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.spring.injection.annot.SpringBean
import java.io.FileNotFoundException
import java.io.IOException

class MarkdownIndexFilePanel(id: String, model: IModel<FilePath>) : IndexFilePanel(id, model) {

    @SpringBean
    private val gitService: GitService? = null

    @SpringBean
    private val markdownService: MarkdownService? = null

    override fun createBody(id: String): Component {
        return try {
            val renderedMarkdown = markdownService!!.parseToHtml(gitService!!.getContentAsString(this.modelObject))
            val body = Label(id, renderedMarkdown).setEscapeModelStrings(false)
            body.add(CssClassAppender("content"))
            body.add(CssClassAppender("markdown"))
            body
        } catch (e: FileNotFoundException) {
            throw AbortWithHttpErrorCodeException(404)
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun renderHead(response: IHeaderResponse) {
        super.renderHead(response)
        response.render(HighlightInitHeaderItem())
    }
}