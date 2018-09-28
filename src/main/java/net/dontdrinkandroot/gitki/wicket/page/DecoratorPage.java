package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.component.BrandLink;
import net.dontdrinkandroot.gitki.wicket.component.feedback.FlashMessagePanel;
import net.dontdrinkandroot.gitki.wicket.component.item.AdminDropdownItem;
import net.dontdrinkandroot.gitki.wicket.component.item.HistoryPageItem;
import net.dontdrinkandroot.gitki.wicket.component.item.SignInPageLinkItem;
import net.dontdrinkandroot.gitki.wicket.component.item.UserDropdownItem;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ModalRequestBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.Navbar;
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.RepeatingNavbarNav;
import net.dontdrinkandroot.wicket.bootstrap.css.BackgroundColor;
import net.dontdrinkandroot.wicket.bootstrap.css.NavbarPosition;
import net.dontdrinkandroot.wicket.bootstrap.css.NavbarStyle;
import net.dontdrinkandroot.wicket.bootstrap.css.Spacing;
import net.dontdrinkandroot.wicket.util.NonStatelessPrintingVisitor;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.visit.Visits;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class DecoratorPage<T> extends ScaffoldPage<T>
{
    public static final String MODAL_ID = "modal";

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
                return new BrandLink(id);
            }

            @Override
            protected void populateCollapseItems(RepeatingView collapseItemView)
            {
                super.populateCollapseItems(collapseItemView);

                RepeatingNavbarNav<Void> navbarLeft = new RepeatingNavbarNav<Void>(collapseItemView.newChildId())
                {
                    @Override
                    protected void populateItems(RepeatingView itemView)
                    {
                        super.populateItems(itemView);
                        DecoratorPage.this.populateNavbarLeftItems(itemView);
                    }
                };
                navbarLeft.add(new CssClassAppender(new Spacing(
                        Spacing.Property.MARGIN,
                        Spacing.Side.RIGHT,
                        Spacing.Size.AUTO
                )));
                collapseItemView.add(navbarLeft);

                RepeatingNavbarNav<Void> navbarRight = new RepeatingNavbarNav<Void>(collapseItemView.newChildId())
                {
                    @Override
                    protected void populateItems(RepeatingView itemView)
                    {
                        super.populateItems(itemView);
                        DecoratorPage.this.populateNavbarRightItems(itemView);
                    }
                };
                collapseItemView.add(navbarRight);
            }
        };
        navbar.setStyle(NavbarStyle.DARK);
        navbar.add(new CssClassAppender(BackgroundColor.DARK));
        navbar.setPosition(NavbarPosition.FIXED_TOP);
        this.add(navbar);

        this.add(new FlashMessagePanel("flashMessages"));

        this.createModal();
    }

    @Override
    protected void onBeforeRender()
    {
        super.onBeforeRender();

        if (null == GitkiWebSession.get().getUser()) {
            // TODO: Remove
            Visits.visit(this, new NonStatelessPrintingVisitor());
        }
    }

    protected void populateNavbarLeftItems(RepeatingView itemView)
    {
        itemView.add(new HistoryPageItem(itemView.newChildId()));
    }

    protected void populateNavbarRightItems(RepeatingView itemView)
    {
        itemView.add(new AdminDropdownItem(itemView.newChildId()));
        itemView.add(new UserDropdownItem(itemView.newChildId()));
        itemView.add(new SignInPageLinkItem(itemView.newChildId()));
    }

    private void createModal()
    {
        this.add(new ModalRequestBehavior(MODAL_ID));
        WebMarkupContainer modalContainer = new WebMarkupContainer(MODAL_ID);
        modalContainer.setOutputMarkupId(true);
        this.add(modalContainer);
    }
}
