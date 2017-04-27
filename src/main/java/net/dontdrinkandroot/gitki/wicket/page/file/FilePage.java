package net.dontdrinkandroot.gitki.wicket.page.file;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.PathBreadcrumb;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathStringModel;
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FilePage extends DecoratorPage<FilePath>
{
    public FilePage(PageParameters parameters)
    {
        super(parameters);
        FilePath path = PageParameterUtils.toFilePath(parameters);
        this.setModel(Model.of(path));
    }

    public FilePage(IModel<FilePath> model)
    {
        super(model);
        PageParameterUtils.from(model.getObject(), this.getPageParameters());
    }

    @Override
    protected IModel<String> createTitleModel()
    {
        return new AbstractPathStringModel(this.getModel());
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        this.add(new PathBreadcrumb<>("breadcrumb", this.getModel()));
        this.add(new Label("heading", new AbstractPathNameModel(this.getModel())));
        this.add(this.createActions("actions"));
    }

    protected Component createActions(String id)
    {
        WebMarkupContainer actions = new WebMarkupContainer(id);
        actions.setVisible(false);

        return actions;
    }
}
