package net.dontdrinkandroot.gitki.wicket.component.button;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.BookmarkablePageButton;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.Page;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.ADMIN)
public class UserAddButton extends BookmarkablePageButton<Void>
{
    public <C extends Page> UserAddButton(String id)
    {
        super(id, UserEditPage.class);
        this.add(new IconBehavior(FontAwesomeIconClass.PLUS.createIcon().setFixedWidth(true)));
    }
}
