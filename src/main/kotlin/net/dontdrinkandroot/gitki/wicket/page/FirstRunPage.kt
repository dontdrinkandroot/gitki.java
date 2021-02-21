package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.service.user.UserService
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.RepeatingAjaxForm
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

    override fun createTitleModel() = Model.of("Initial Configuration")

    override fun onInitialize() {
        super.onInitialize()
        val form: RepeatingAjaxForm<User> = object : RepeatingAjaxForm<User>("form", model) {
            override fun populateFormGroups(formGroupView: RepeatingView) {
                val formGroupFirstName = FormGroupInputText(
                    formGroupView.newChildId(),
                    model.writableProperty(User::firstName),
                    Model.of("First name")
                )
                formGroupFirstName.setRequired(true)
                formGroupFirstName.addAjaxValidation()
                formGroupView.add(formGroupFirstName)

                val formGroupLastName = FormGroupInputText(
                    formGroupView.newChildId(),
                    model.writableProperty(User::lastName),
                    Model.of("Last name")
                )
                formGroupLastName.setRequired(true)
                formGroupLastName.addAjaxValidation()
                formGroupView.add(formGroupLastName)

                // TODO: readd
//                val formGroupEmail = FormGroupInputEmail(
//                    formGroupView.newChildId(),
//                    model.writableProperty(User::email),
//                    Model.of("Email")
//                )
//                formGroupEmail.setRequired(true)
//                formGroupEmail.addAjaxValidation()
//                formGroupView.add(formGroupEmail)

                val formGroupPassword = FormGroupInputPassword(
                    formGroupView.newChildId(),
                    passwordModel,
                    Model.of("Password")
                )
                formGroupPassword.setRequired(true)
                formGroupPassword.addAjaxValidation()
                formGroupView.add(formGroupPassword)
            }

            override fun populateActions(buttonView: RepeatingView) {
                buttonView.add(SubmitLabelButton(buttonView.newChildId(), Model.of("Create")))
            }

            override fun onSubmit(target: AjaxRequestTarget?) {
                super.onSubmit(target)
                var admin = this@FirstRunPage.modelObject
                admin = userService!!.save(admin!!, passwordModel.getObject())
            }

            override fun onAfterSubmit(target: AjaxRequestTarget?) {
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