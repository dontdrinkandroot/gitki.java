package net.dontdrinkandroot.gitki.wicket.component.button

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import net.dontdrinkandroot.wicket.kmodel.kModel
import net.dontdrinkandroot.wicket.kmodel.model
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel

@Render(Role.ADMIN)
class UserAddButton(
    id: String,
    buttonStyleModel: IModel<ButtonStyle> = model(ButtonStyle.PRIMARY),
    buttonSizeModel: IModel<ButtonSize> = model(ButtonSize.LARGE),
) : Link<Void>(id) {

    init {
        this.add(ButtonBehavior(buttonStyleModel, buttonSizeModel))
        this.add(CssClassAppender(GitkiCssClass.BTN_ICON))
        this.add(IconBehavior(FontAwesome5IconClass.PLUS.createIcon().apply { fixedWidth = true }))
    }

    override fun onClick() {
        setResponsePage(UserEditPage(kModel(User(email = "", firstName = "", lastName = ""))))
    }
}