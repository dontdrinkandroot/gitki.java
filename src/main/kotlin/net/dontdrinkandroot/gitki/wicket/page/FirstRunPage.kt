package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.service.user.UserService
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.RepeatingAjaxForm
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputEmail
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputPassword
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.model.writableProperty
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean
import javax.servlet.http.HttpServletResponse

class FirstRunPage(parameters: PageParameters) : DecoratorPage<User>(parameters) {

    @SpringBean
    private val userService: UserService? = null
    private val passwordModel: IModel<String> = Model()
    override fun createTitleModel(): IModel<String> {
        return Model.of("Initial Configuration")
    }

    override fun onInitialize() {
        super.onInitialize()
        val form: RepeatingAjaxForm<Void> = object : RepeatingAjaxForm<Void>("form") {
            override fun populateFormGroups(formGroupView: RepeatingView) {
                val formGroupFirstName = FormGroupInputText(
                    formGroupView.newChildId(),
                    Model.of("First name"),
                    this@FirstRunPage.model.writableProperty(User::firstName)
                )
                formGroupFirstName.setRequired(true)
                formGroupFirstName.addDefaultAjaxInputValidation()
                formGroupView.add(formGroupFirstName)
                val formGroupLastName = FormGroupInputText(
                    formGroupView.newChildId(),
                    Model.of("Last name"),
                    this@FirstRunPage.model.writableProperty(User::lastName)
                )
                formGroupLastName.setRequired(true)
                formGroupLastName.addDefaultAjaxInputValidation()
                formGroupView.add(formGroupLastName)
                val formGroupEmail = FormGroupInputEmail(
                    formGroupView.newChildId(),
                    Model.of("Email"),
                    this@FirstRunPage.model.writableProperty(User::email)
                )
                formGroupEmail.setRequired(true)
                formGroupEmail.addDefaultAjaxInputValidation()
                formGroupView.add(formGroupEmail)
                val formGroupPassword = FormGroupInputPassword(
                    formGroupView.newChildId(),
                    Model.of("Password"),
                    passwordModel
                )
                formGroupPassword.setRequired(true)
                formGroupPassword.addDefaultAjaxInputValidation()
                formGroupView.add(formGroupPassword)
            }

            override fun populateActions(buttonView: RepeatingView) {
                buttonView.add(SubmitLabelButton(buttonView.newChildId(), Model.of("Create")))
            }

            override fun onSubmit(target: AjaxRequestTarget) {
                super.onSubmit(target)
                var admin = this@FirstRunPage.modelObject
                admin = userService!!.save(admin!!, passwordModel.getObject())
            }

            override fun onAfterSubmit(target: AjaxRequestTarget) {
                super.onAfterSubmit(target)
                this@FirstRunPage.setResponsePage(SignInPage::class.java)
            }
        }
        this.add(form)
    }

    init {

        /* This page is only accessible when no admin user has been created */if (userService!!.hasAdminUser()) {
            throw AbortWithHttpErrorCodeException(HttpServletResponse.SC_FORBIDDEN)
        }
        val admin = User()
        admin.role = Role.ADMIN
        this.model = Model.of(admin)
    }
}