package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.service.user.UserService
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.RepeatingAjaxForm
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputEmail
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputPassword
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.css.Spacing
import net.dontdrinkandroot.wicket.kmodel.*
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean
import javax.servlet.http.HttpServletResponse

class FirstRunPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    @SpringBean
    private lateinit var userService: UserService

    private val firstNameModel: KModel<String?> = kModel(null)

    private val lastNameModel: KModel<String?> = kModel(null)

    private val emailModel: KModel<String?> = kModel(null)

    private val passwordModel: KModel<String?> = kModel(null)

    override fun createTitleModel() = kModel("Initial Configuration")

    init {
        /* This page is only accessible when no admin user has been created */
        if (userService.hasAdminUser()) throw AbortWithHttpErrorCodeException(HttpServletResponse.SC_FORBIDDEN)

    }

    override fun onInitialize() {
        super.onInitialize()
        val form: RepeatingAjaxForm<Void> = object : RepeatingAjaxForm<Void>("form") {
            override fun populateFormGroups(formGroupView: RepeatingView) {
                val formGroupFirstName = FormGroupInputText(
                    formGroupView.newChildId(),
                    firstNameModel,
                    ValueKModel("First name")
                )
                formGroupFirstName.setRequired(true)
                formGroupFirstName.addAjaxValidation()
                formGroupView.add(formGroupFirstName)

                val formGroupLastName = FormGroupInputText(
                    formGroupView.newChildId(),
                    lastNameModel,
                    ValueKModel("Last name")
                )
                formGroupLastName.setRequired(true)
                formGroupLastName.addAjaxValidation()
                formGroupView.add(formGroupLastName)

                val formGroupEmail = FormGroupInputEmail(
                    formGroupView.newChildId(),
                    emailModel,
                    ValueKModel("Email")
                )
                formGroupEmail.setRequired(true)
                formGroupEmail.addAjaxValidation()
                formGroupView.add(formGroupEmail)

                val formGroupPassword = FormGroupInputPassword(
                    formGroupView.newChildId(),
                    passwordModel,
                    ValueKModel("Password")
                )
                formGroupPassword.setRequired(true)
                formGroupPassword.addAjaxValidation()
                formGroupView.add(formGroupPassword)

                formGroupView.forEach { it.add(CssClassAppender(Spacing.MARGIN_BOTTOM_FULL)) }
            }

            override fun populateActions(buttonView: RepeatingView) {
                buttonView.add(SubmitLabelButton(buttonView.newChildId(), kModel("Create")))
            }

            override fun onSubmit(target: AjaxRequestTarget?) {
                super.onSubmit(target)

                val admin = User(
                    firstName = firstNameModel.value!!,
                    lastName = lastNameModel.value!!,
                    email = emailModel.value!!,
                    role = Role.ADMIN
                )
                userService.save(admin, passwordModel.getObject())
            }

            override fun onAfterSubmit(target: AjaxRequestTarget?) {
                super.onAfterSubmit(target)
                this@FirstRunPage.setResponsePage(SignInPage::class.java)
            }
        }
        this.add(form)
    }
}