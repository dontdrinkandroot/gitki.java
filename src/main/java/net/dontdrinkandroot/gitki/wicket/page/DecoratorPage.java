package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.wicket.bootstrap.behavior.ModalRequestBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.NavBar;
import net.dontdrinkandroot.wicket.bootstrap.page.BootstrapPage;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class DecoratorPage<T> extends BootstrapPage<T>
{
    private IModel<String> titleModel;

    public DecoratorPage()
    {
    }

    public DecoratorPage(PageParameters parameters)
    {
        super(parameters);
    }

    public DecoratorPage(IModel<T> model)
    {
        super(model);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        NavBar navBar = new NavBar("navbar")
        {
            @Override
            protected Component createBrand(String id)
            {
                return new Label(id, "GitKi");
            }
        };
        this.add(navBar);

        this.createModal();
    }

    private void createModal()
    {
        this.add(new ModalRequestBehavior("modal"));
        WebMarkupContainer modalContainer = new WebMarkupContainer("modal");
        modalContainer.setOutputMarkupId(true);
        this.add(modalContainer);
    }

    @Override
    protected Component createPageTitle(String id)
    {
        this.titleModel = this.createTitleModel();
        Label pageTitleLabel = new Label(id, this.titleModel);
        return pageTitleLabel;
    }

    public IModel<String> getTitleModel()
    {
        return this.titleModel;
    }

    protected abstract IModel<String> createTitleModel();
}
