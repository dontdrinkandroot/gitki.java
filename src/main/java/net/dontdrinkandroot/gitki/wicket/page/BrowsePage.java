package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.component.PathBreadcrumb;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathAbsoluteStringModel;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(value = Role.WATCHER, anonymous = true)
public class BrowsePage<T extends AbstractPath> extends DecoratorPage<T>
{
    public BrowsePage(PageParameters parameters)
    {
        super(parameters);
    }

    public BrowsePage(IModel<T> model)
    {
        super(model);
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
}
