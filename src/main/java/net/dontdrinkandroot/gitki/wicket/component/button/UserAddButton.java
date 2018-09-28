package net.dontdrinkandroot.gitki.wicket.component.button;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.component.button.Button;
import net.dontdrinkandroot.wicket.css.StringCssClass;
import org.apache.wicket.Page;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.ADMIN)
public class UserAddButton extends Button<Void>
{
    public <C extends Page> UserAddButton(String id)
    {
        super(id);
        //this.add(new IconBehavior(FontAwesomeIconClass.PLUS.createIcon().setFixedWidth(true)));
        this.add(new CssClassAppender(new StringCssClass("fas fa-plus")));
    }

    @Override
    public void onClick()
    {
        this.setResponsePage(new UserEditPage(Model.of(new User())));
    }
}
