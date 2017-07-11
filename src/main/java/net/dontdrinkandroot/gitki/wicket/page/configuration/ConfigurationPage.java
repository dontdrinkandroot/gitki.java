package net.dontdrinkandroot.gitki.wicket.page.configuration;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.core.env.Environment;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@AuthorizeInstantiation(Role.Constants.ADMIN)
public class ConfigurationPage extends DecoratorPage
{
    @SpringBean
    private GitService gitService;

    @SpringBean
    private Environment environment;

    @SpringBean
    private ConfigurationService configurationService;

    @Override
    protected IModel<String> createTitleModel()
    {
        return Model.of("Configuration");
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        this.add(new Label("repositoryPath", this.gitService.getRepositoryPath().toString()));
        this.add(new Label(
                "anonymousBrowsingEnabled",
                Boolean.toString(this.configurationService.isAnonymousBrowsingEnabled())
        ));
        this.add(new Label("defaultProfiles", String.join(",", this.environment.getDefaultProfiles())));
        this.add(new Label("activeProfiles", String.join(",", this.environment.getActiveProfiles())));
    }
}
