package net.dontdrinkandroot.gitki.wicket.dropdown;

import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.behavior.DropdownToggleBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.dropdown.DropdownMenu;
import net.dontdrinkandroot.wicket.bootstrap.css.BootstrapCssClass;
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment;
import net.dontdrinkandroot.wicket.css.StringCssClass;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

public abstract class OptionLinkDropdown extends Panel
{
    public OptionLinkDropdown(String id)
    {
        super(id);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        this.add(new CssClassAppender(BootstrapCssClass.DROPDOWN));

        Component link = new WebMarkupContainer("link");
        link.add(new IconBehavior(new StringCssClass("fas fa-ellipsis-v fa-fw")));
        link.add(new DropdownToggleBehavior());
        link.add(new CssClassAppender(GitkiCssClass.CARET_OFF));
        this.add(link);

        DropdownMenu dropdownMenu = new DropdownMenu("dropdownMenu")
        {
            @Override
            protected void populateItems(RepeatingView itemView)
            {
                OptionLinkDropdown.this.populateItems(itemView);
            }
        };
        dropdownMenu.setAlignment(DropdownAlignment.RIGHT);
        this.add(dropdownMenu);
    }

    protected abstract void populateItems(RepeatingView itemView);
}
