package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import net.dontdrinkandroot.gitki.wicket.component.item.UserDropdownItem;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ModalRequestBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.NavBar;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(role = Role.WATCHER, allowAnonymous = true)
public abstract class DecoratorPage<T> extends ScaffoldPage<T>
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
                BookmarkablePageLink<Void> link =
                        new BookmarkablePageLink<>(id, GitkiWebApplication.get().getHomePage());
                link.setBody(Model.of("GitKi"));

                return link;
            }

            @Override
            protected void populateNavbarLeftItems(RepeatingView itemView)
            {
                super.populateNavbarLeftItems(itemView);
            }

            @Override
            protected void populateNavbarRightItems(RepeatingView itemView)
            {
                super.populateNavbarRightItems(itemView);
                itemView.add(new UserDropdownItem(itemView.newChildId()));
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
}
