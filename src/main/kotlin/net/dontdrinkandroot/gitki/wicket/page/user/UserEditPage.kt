package net.dontdrinkandroot.gitki.wicket.page.user

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.service.user.UserService
import net.dontdrinkandroot.gitki.wicket.form.UserEditForm
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.page.admin.UserListPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.bootstrap.css.grid.ColumnSizeStack
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean

@Instantiate(Role.WATCHER)
class UserEditPage : UserPage<User> {

    @SpringBean
    private lateinit var userService: UserService

    constructor(parameters: PageParameters) : super(parameters) {
        val user = userService.find(getGitkiSession().user!!.id!!)
            ?: throw RuntimeException("The user of the session could not be found in the database")
        this.model = Model(user)
    }

    constructor(model: IModel<User>) : super(model)

    override fun createTitleModel() = StringResourceModel("gitki.user_edit")

    override fun onInitialize() {
        super.onInitialize()
        val form: UserEditForm = object : UserEditForm("form", this.model) {
            override fun onAfterSubmit(target: AjaxRequestTarget?) {
                super.onAfterSubmit(target)
                if (getGitkiSession().hasRole(Role.ADMIN)) {
                    this.setResponsePage(UserListPage::class.java)
                }
            }
        }
        form.setHorizontal(ColumnSizeStack.FORM_DEFAULT)
        this.add(form)
    }
}