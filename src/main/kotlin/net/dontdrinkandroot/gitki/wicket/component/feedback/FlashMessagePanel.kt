package net.dontdrinkandroot.gitki.wicket.component.feedback

import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass
import net.dontdrinkandroot.wicket.behavior.addCssClass
import net.dontdrinkandroot.wicket.bootstrap.component.feedback.FencedFeedbackPanel
import org.apache.wicket.ajax.AjaxRequestHandler
import org.apache.wicket.event.IEvent
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.head.OnLoadHeaderItem

class FlashMessagePanel(id: String) : FencedFeedbackPanel(id) {

    init {
        this.outputMarkupId = true
        addCssClass(GitkiCssClass.FLASH_MESSAGES)
    }

    override fun onEvent(event: IEvent<*>) {
        super.onEvent(event)
        if (this.anyMessage() && event.payload is AjaxRequestHandler) {
            val ajaxRequestHandler = event.payload as AjaxRequestHandler
            ajaxRequestHandler.add(this)
            ajaxRequestHandler.appendJavaScript(hideScript)
        }
    }

    override fun renderHead(response: IHeaderResponse) {
        response.render(OnLoadHeaderItem(hideScript))
    }

    protected val hideScript: String
        protected get() = String.format(
            "$('#%s').delay(2000).slideUp(1000);",
            this.markupId
        )
}