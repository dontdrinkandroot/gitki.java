package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.wicket.bootstrap.behavior.ModalRequestBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.navbar.NavBar;
import net.dontdrinkandroot.wicket.bootstrap.page.BootstrapPage;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DecoratorPage<T> extends BootstrapPage<T>
{
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
        this.add(modalContainer);
    }
}
