package net.dontdrinkandroot.gitki.wicket.page.admin

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.kmodel.ValueKModel
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean
import org.springframework.core.env.Environment

@Instantiate(Role.ADMIN)
class ConfigurationPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    @SpringBean
    private lateinit var gitService: GitService

    @SpringBean
    private lateinit var environment: Environment

    @SpringBean
    private lateinit var configurationService: ConfigurationService

    override fun createTitleModel() = ValueKModel("Configuration")

    override fun onInitialize() {
        super.onInitialize()
        this.add(Label("repositoryPath", gitService.repositoryPath.toString()))
        this.add(
            Label(
                "anonymousBrowsingEnabled",
                configurationService.isAnonymousBrowsingEnabled.toString()
            )
        )
        this.add(Label("defaultProfiles", java.lang.String.join(",", *environment.defaultProfiles)))
        this.add(Label("activeProfiles", java.lang.String.join(",", *environment.activeProfiles)))
    }
}