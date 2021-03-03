package net.dontdrinkandroot.gitki.wicket.page.admin

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.bootstrap.component.button.button
import net.dontdrinkandroot.wicket.kmodel.ValueKModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.mapper.parameter.PageParameters

@Instantiate(Role.ADMIN)
class ErrorTestPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    override fun createTitleModel() = ValueKModel("Errors")

    override fun onInitialize() {

        super.onInitialize()

        val notFoundButton = button<Void>(
            "notFound",
            bodyModel = Model("Not Found")
        ) { throw AbortWithHttpErrorCodeException(404) }
        this.add(notFoundButton)

        val lockedButton = button<Void>(
            "locked",
            bodyModel = Model("Locked")
        ) { throw AbortWithHttpErrorCodeException(423) }
        this.add(lockedButton)

        val runtimeButton = button<Void>(
            "runtime",
            bodyModel = Model("Runtime")
        ) { throw RuntimeException("This is a runtime exception message") }
        this.add(runtimeButton)
    }
}