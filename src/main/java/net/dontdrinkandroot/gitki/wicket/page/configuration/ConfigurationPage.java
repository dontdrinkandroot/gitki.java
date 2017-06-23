package net.dontdrinkandroot.gitki.wicket.page.configuration;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.ADMIN)
public class ConfigurationPage extends DecoratorPage
{
    @SpringBean
    private GitService gitService;

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
    }
}
