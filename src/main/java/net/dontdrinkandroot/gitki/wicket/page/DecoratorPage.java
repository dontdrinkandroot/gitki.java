package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.component.item.LoginLinkItem;
import net.dontdrinkandroot.gitki.wicket.component.item.UserDropdownItem;
import net.dontdrinkandroot.gitki.wicket.component.item.UserListPageItem;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ModalRequestBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.Navbar;
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.NavbarNav;
import net.dontdrinkandroot.wicket.bootstrap.css.NavbarAlignment;
import net.dontdrinkandroot.wicket.bootstrap.css.NavbarPosition;
import net.dontdrinkandroot.wicket.utils.NonStatelessPrintingVisitor;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.visit.Visits;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(value = Role.WATCHER, allowAnonymous = true)
public abstract class DecoratorPage<T> extends ScaffoldPage<T>
{
    public static final String MODAL_ID = "modal";

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

        Navbar navbar = new Navbar("navbar")
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
            protected void populateCollapseItems(RepeatingView collapseItemView)
            {
                super.populateCollapseItems(collapseItemView);

                NavbarNav navbarLeft = new NavbarNav(collapseItemView.newChildId())
                {
                    @Override
                    protected void populateItems(RepeatingView itemView)
                    {
                        super.populateItems(itemView);
                        DecoratorPage.this.populateNavbarLeftItems(itemView);
                    }
                };
                collapseItemView.add(navbarLeft);

                NavbarNav navbarRight = new NavbarNav(collapseItemView.newChildId())
                {
                    @Override
                    protected void populateItems(RepeatingView itemView)
                    {
                        super.populateItems(itemView);
                        DecoratorPage.this.populateNavbarRightItems(itemView);
                    }
                };
                navbarRight.setAlignment(NavbarAlignment.RIGHT);
                collapseItemView.add(navbarRight);
            }
        };
        navbar.setPosition(NavbarPosition.FIXED_TOP);
        this.add(navbar);

        this.createModal();
    }

    @Override
    protected void onBeforeRender()
    {
        super.onBeforeRender();

        if (null == GitkiWebSession.get().getUser()) {
            Visits.visit(this, new NonStatelessPrintingVisitor());
        }
    }

    protected void populateNavbarLeftItems(RepeatingView itemView)
    {
    }

    protected void populateNavbarRightItems(RepeatingView itemView)
    {
        itemView.add(new UserListPageItem(itemView.newChildId()));
        itemView.add(new UserDropdownItem(itemView.newChildId()));
        itemView.add(new LoginLinkItem(itemView.newChildId()));
    }

    private void createModal()
    {
        this.add(new ModalRequestBehavior(MODAL_ID));
        WebMarkupContainer modalContainer = new WebMarkupContainer(MODAL_ID);
        modalContainer.setOutputMarkupId(true);
        this.add(modalContainer);
    }
}
