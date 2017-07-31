package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.component.PathBreadcrumb;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathAbsoluteStringModel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class BrowsePage<T extends AbstractPath> extends DecoratorPage<T>
{
    @SpringBean
    private GitService gitService;

    public BrowsePage(PageParameters parameters)
    {
        super(parameters);
        this.checkAccess();
    }

    public BrowsePage(IModel<T> model)
    {
        super(model);
        this.checkAccess();
    }

    private void checkAccess()
    {
        GitkiWebSession gitkiWebSession = GitkiWebSession.get();
        if (!gitkiWebSession.isSignedIn()) {
            gitkiWebSession.assertAnonymousBrowsing(BrowsePage.class);
        }
    }

    @Override
    protected IModel<String> createTitleModel()
    {
        return new AbstractPathAbsoluteStringModel(this.getModel());
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        RepeatingView primaryButtonView = new RepeatingView("primaryButton");
        this.populatePrimaryButtons(primaryButtonView);
        this.add(primaryButtonView);

        this.add(new PathBreadcrumb<>("breadcrumb", this.getModel()));
    }

    protected void populatePrimaryButtons(RepeatingView view)
    {
        /* Hook */
    }

    public GitService getGitService()
    {
        return this.gitService;
    }
}
