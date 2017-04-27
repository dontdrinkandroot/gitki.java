package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.wicket.bootstrap.headeritem.FontAwesomeCssHeaderItem;
import net.dontdrinkandroot.wicket.bootstrap.page.BootstrapPage;
import org.apache.wicket.Component;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class ScaffoldPage<T> extends BootstrapPage<T>
{
    private IModel<String> titleModel;

    public ScaffoldPage()
    {
    }

    public ScaffoldPage(PageParameters parameters)
    {
        super(parameters);
    }

    public ScaffoldPage(IModel<T> model)
    {
        super(model);
    }

    @Override
    protected Component createPageTitle(String id)
    {
        this.titleModel = this.createTitleModel();
        return new Label(id, this.titleModel);
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);
        response.render(new FontAwesomeCssHeaderItem());
    }

    public IModel<String> getTitleModel()
    {
        return this.titleModel;
    }

    protected abstract IModel<String> createTitleModel();
}
