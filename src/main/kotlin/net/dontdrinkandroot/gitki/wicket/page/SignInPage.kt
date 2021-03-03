package net.dontdrinkandroot.gitki.wicket.page

import com.giffing.wicket.spring.boot.context.scan.WicketSignInPage
import net.dontdrinkandroot.gitki.service.user.UserService
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.head.AppCssHeaderItem
import net.dontdrinkandroot.wicket.bootstrap.page.SignInPage
import org.apache.wicket.RestartResponseAtInterceptPageException
import org.apache.wicket.markup.head.HeaderItem
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean

@WicketSignInPage
open class SignInPage(parameters: PageParameters) : SignInPage(parameters) {

    @SpringBean
    private lateinit var userService: UserService

    override fun onInitialize() {
        assertAdminUserExists()
        super.onInitialize()
    }

    private fun assertAdminUserExists() {
        if (!userService.hasAdminUser()) throw RestartResponseAtInterceptPageException(FirstRunPage::class.java)
    }

    override fun createUsernameLabelModel(): IModel<String> = Model.of("Email")

    override val signedIn: Boolean
        get() = null != getGitkiSession().user

    override fun signIn(username: String, password: String): Boolean = getGitkiSession()
        .signIn(username, password)

    override val bootstrapHeaderItem: HeaderItem
        get() = AppCssHeaderItem()
}