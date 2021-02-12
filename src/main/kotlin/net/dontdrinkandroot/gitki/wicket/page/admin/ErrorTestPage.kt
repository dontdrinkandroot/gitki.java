package net.dontdrinkandroot.gitki.wicket.page.admin

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.bootstrap.component.button.ButtonLink
import org.apache.wicket.model.Model
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.mapper.parameter.PageParameters

@Instantiate(Role.ADMIN)
class ErrorTestPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    override fun createTitleModel() = Model("Errors")

    override fun onInitialize() {

        super.onInitialize()

        val notFoundButton = object : ButtonLink<Void>("notFound") {
            override fun onClick() {
                throw AbortWithHttpErrorCodeException(404)
            }
        }.setBody(Model.of("Not Found"))
        this.add(notFoundButton)

        val lockedButton = object : ButtonLink<Void>("locked") {
            override fun onClick() {
                throw AbortWithHttpErrorCodeException(423)
            }
        }.setBody(Model.of("Locked"))
        this.add(lockedButton)

        val runtimeButton = object : ButtonLink<Void>("runtime") {
            override fun onClick() {
                throw RuntimeException("This is a runtime exception message")
            }
        }.setBody(Model.of("Runtime"))
        this.add(runtimeButton)
    }
}