package net.dontdrinkandroot.gitki.wicket.component.button;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.BookmarkablePageButton;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@AuthorizeAction(action = Action.RENDER, roles = Role.Constants.ADMIN)
public class UserAddButton extends BookmarkablePageButton<Void>
{
    public <C extends Page> UserAddButton(String id)
    {
        super(id, UserEditPage.class);
        this.add(new IconBehavior(FontAwesomeIconClass.PLUS.createIcon().setFixedWidth(true)));
    }
}
