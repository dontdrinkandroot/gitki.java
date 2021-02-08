package net.dontdrinkandroot.gitki.wicket

import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication
import net.dontdrinkandroot.gitki.wicket.page.FirstRunPage
import net.dontdrinkandroot.gitki.wicket.page.HistoryPage
import net.dontdrinkandroot.gitki.wicket.page.SignInPage
import net.dontdrinkandroot.gitki.wicket.page.admin.ConfigurationPage
import net.dontdrinkandroot.gitki.wicket.page.admin.UserListPage
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.page.test.FeedbackTestPage
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage
import net.dontdrinkandroot.gitki.wicket.requestmapper.BrowseRequestMapper
import net.dontdrinkandroot.gitki.wicket.requestmapper.RawRequestMapper
import net.dontdrinkandroot.gitki.wicket.resource.ExternalJQueryResourceReference
import net.dontdrinkandroot.gitki.wicket.resource.RawResource
import net.dontdrinkandroot.gitki.wicket.security.GitkiAuthorizationStrategy
import org.apache.wicket.Application
import org.apache.wicket.Page
import org.apache.wicket.Session
import org.apache.wicket.injection.Injector
import org.apache.wicket.request.Request
import org.apache.wicket.request.Response
import org.apache.wicket.request.resource.SharedResourceReference
import org.springframework.stereotype.Component

fun getGitkiApplication() = Application.get() as GitkiWebApplication

@Component
class GitkiWebApplication : WicketBootSecuredWebApplication() {

    override fun init() {
        super.init()
        javaScriptLibrarySettings.jQueryReference = ExternalJQueryResourceReference()
        securitySettings.authorizationStrategy = applicationContext.getBean(
            GitkiAuthorizationStrategy::class.java
        )
        val browseRequestMapper = BrowseRequestMapper()
        Injector.get().inject(browseRequestMapper)
        mount(browseRequestMapper)
        mount(RawRequestMapper())
        mountPage("signin", SignInPage::class.java)
        mountPage("firstrun", FirstRunPage::class.java)
        mountPage("configuration", ConfigurationPage::class.java)
        mountPage("history", HistoryPage::class.java)
        mountPage("users", UserListPage::class.java)
        mountPage("users/edit", UserEditPage::class.java)

        // Testing
        mountPage("test/feedback", FeedbackTestPage::class.java)
        val rawResource = RawResource()
        Injector.get().inject(rawResource)
        sharedResources.add("raw", rawResource)
        mountResource("raw", SharedResourceReference("raw"))
    }

    override fun getHomePage(): Class<out Page?> = DirectoryPage::class.java

    override fun newSession(request: Request, response: Response): Session = GitkiWebSession(request)
}