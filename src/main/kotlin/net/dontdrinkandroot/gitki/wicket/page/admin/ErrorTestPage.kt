package net.dontdrinkandroot.gitki.wicket.page.admin

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.bootstrap.component.button.Button
import org.apache.wicket.model.Model
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.mapper.parameter.PageParameters

@Instantiate(Role.ADMIN)
class ErrorTestPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    override fun createTitleModel() = Model("Errors")

    override fun onInitialize() {

        super.onInitialize()

        val notFoundButton = Button<Void>(
            "notFound",
            bodyModel = Model("Not Found"),
            onClickHandler = { throw AbortWithHttpErrorCodeException(404) })
        this.add(notFoundButton)

        val lockedButton = Button<Void>(
            "locked",
            bodyModel = Model("Locked"),
            onClickHandler = { throw AbortWithHttpErrorCodeException(423) })
        this.add(lockedButton)

        val runtimeButton = Button<Void>(
            "runtime",
            bodyModel = Model("Runtime"),
            onClickHandler = { throw RuntimeException("This is a runtime exception message") })
        this.add(runtimeButton)
    }
}