package net.dontdrinkandroot.gitki.wicket.page.test

import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.wicket.bootstrap.component.button.ajaxButtonLink
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import net.dontdrinkandroot.wicket.kmodel.kModel
import org.apache.wicket.request.mapper.parameter.PageParameters

class FeedbackTestPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    override fun createTitleModel() = kModel("Feedback")

    override fun onInitialize() {

        super.onInitialize()

        this.session.info("This is an initial message")

        add(ajaxButtonLink("info", buttonStyle = ButtonStyle.INFO) {
            this@FeedbackTestPage.session.info("This is an info message")
        })

        add(ajaxButtonLink("success", buttonStyle = ButtonStyle.SUCCESS) {
            this@FeedbackTestPage.session.info("This is a success message")
        })

        add(ajaxButtonLink("warn", buttonStyle = ButtonStyle.WARNING) {
            this@FeedbackTestPage.session.info("This is a warning message")
        })

        add(ajaxButtonLink("error", buttonStyle = ButtonStyle.DANGER) {
            this@FeedbackTestPage.session.info("This is a error message")
        })
    }
}