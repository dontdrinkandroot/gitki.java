package net.dontdrinkandroot.gitki.wicket.page.test

import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxButton
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters

class FeedbackTestPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    override fun createTitleModel() = Model("Feedback")

    override fun onInitialize() {
        super.onInitialize()
        this.session.info("This is an initial message")
        val infoButton: AjaxButton<Void> = object : AjaxButton<Void>("info") {
            override fun onClick(target: AjaxRequestTarget) {
                this@FeedbackTestPage.session.info("This is an info message")
            }
        }
        infoButton.setButtonStyle(ButtonStyle.INFO)
        this.add(infoButton)

        val successButton: AjaxButton<Void> = object : AjaxButton<Void>("success") {
            override fun onClick(target: AjaxRequestTarget) {
                this@FeedbackTestPage.session.success("This is a success message")
            }
        }
        successButton.setButtonStyle(ButtonStyle.SUCCESS)
        this.add(successButton)

        val warnButton: AjaxButton<Void> = object : AjaxButton<Void>("warn") {
            override fun onClick(target: AjaxRequestTarget) {
                this@FeedbackTestPage.session.warn("This is a warning message")
            }
        }
        warnButton.setButtonStyle(ButtonStyle.WARNING)
        this.add(warnButton)

        val errorButton: AjaxButton<Void> = object : AjaxButton<Void>("error") {
            override fun onClick(target: AjaxRequestTarget) {
                this@FeedbackTestPage.session.error("This is an error message")
            }
        }
        errorButton.setButtonStyle(ButtonStyle.DANGER)
        this.add(errorButton)
    }
}