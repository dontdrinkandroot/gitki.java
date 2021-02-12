package net.dontdrinkandroot.gitki.wicket.component.button

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.button.ButtonLink
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import net.dontdrinkandroot.wicket.model.model
import org.apache.wicket.model.Model

@Render(Role.ADMIN)
class UserAddButton(id: String) :
    ButtonLink<Void>(id, buttonStyleModel = ButtonStyle.PRIMARY.model(), buttonSizeModel = ButtonSize.LARGE.model()) {

    override fun onClick() {
        this.setResponsePage(UserEditPage(Model.of(User())))
    }

    init {
        this.add(CssClassAppender(GitkiCssClass.BTN_ICON))
        this.add(IconBehavior(FontAwesome5IconClass.PLUS.createIcon().apply { fixedWidth = true }))
    }
}