package net.dontdrinkandroot.gitki.wicket.component.button

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.component.button.Button
import net.dontdrinkandroot.wicket.css.StringCssClass
import org.apache.wicket.model.Model

@Render(Role.ADMIN)
class UserAddButton(id: String) : Button<Void>(id) {

    override fun onClick() {
        this.setResponsePage(UserEditPage(Model.of(User())))
    }

    init {
        //this.add(new IconBehavior(FontAwesomeIconClass.PLUS.createIcon().setFixedWidth(true)));
        this.add(CssClassAppender(StringCssClass("fas fa-plus")))
    }
}