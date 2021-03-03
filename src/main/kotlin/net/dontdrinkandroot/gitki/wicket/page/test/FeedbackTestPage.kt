package net.dontdrinkandroot.gitki.wicket.page.test

import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.wicket.bootstrap.component.button.ajaxButton
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import net.dontdrinkandroot.wicket.kmodel.ValueKModel
import org.apache.wicket.request.mapper.parameter.PageParameters

class FeedbackTestPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    override fun createTitleModel() = ValueKModel("Feedback")

    override fun onInitialize() {

        super.onInitialize()

        this.session.info("This is an initial message")

        add(ajaxButton("info", buttonStyle = ButtonStyle.INFO) {
            this@FeedbackTestPage.session.info("This is an info message")
        })

        add(ajaxButton("success", buttonStyle = ButtonStyle.SUCCESS) {
            this@FeedbackTestPage.session.info("This is a success message")
        })

        add(ajaxButton("warn", buttonStyle = ButtonStyle.WARNING) {
            this@FeedbackTestPage.session.info("This is a warning message")
        })

        add(ajaxButton("error", buttonStyle = ButtonStyle.DANGER) {
            this@FeedbackTestPage.session.info("This is a error message")
        })
    }
}