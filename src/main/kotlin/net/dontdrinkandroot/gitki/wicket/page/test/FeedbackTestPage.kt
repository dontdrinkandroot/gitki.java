package net.dontdrinkandroot.gitki.wicket.page.test

import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxButton
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters

class FeedbackTestPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    override fun createTitleModel() = Model("Feedback")

    override fun onInitialize() {

        super.onInitialize()

        this.session.info("This is an initial message")

        add(AjaxButton<Void>("info", buttonStyleModel = Model(ButtonStyle.INFO), onClickHandler = {
            this@FeedbackTestPage.session.info("This is an info message")
        }))

        add(AjaxButton<Void>("success", buttonStyleModel = Model(ButtonStyle.SUCCESS), onClickHandler = {
            this@FeedbackTestPage.session.info("This is a success message")
        }))

        add(AjaxButton<Void>("warn", buttonStyleModel = Model(ButtonStyle.WARNING), onClickHandler = {
            this@FeedbackTestPage.session.info("This is a warning message")
        }))

        add(AjaxButton<Void>("error", buttonStyleModel = Model(ButtonStyle.DANGER), onClickHandler = {
            this@FeedbackTestPage.session.info("This is a error message")
        }))
    }
}