package net.dontdrinkandroot.gitki.wicket.form

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.service.user.UserService
import net.dontdrinkandroot.gitki.wicket.choicerenderer.IsoLanguageChoiceRenderer
import net.dontdrinkandroot.gitki.wicket.choicerenderer.ZoneIdChoiceRenderer
import net.dontdrinkandroot.gitki.wicket.getCurrentUser
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.RepeatingAjaxForm
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputEmail
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputPassword
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect
import net.dontdrinkandroot.wicket.bootstrap.css.Spacing
import net.dontdrinkandroot.wicket.kmodel.kModel
import net.dontdrinkandroot.wicket.kmodel.kModelValue
import net.dontdrinkandroot.wicket.kmodel.writableProperty
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.model.util.ListModel
import org.apache.wicket.spring.injection.annot.SpringBean
import java.time.ZoneId
import java.util.*

open class UserEditForm(id: String, model: IModel<User>) : RepeatingAjaxForm<User>(id, model) {

    @SpringBean
    private lateinit var userService: UserService

    private val newPasswordModel: IModel<String> = Model()

    override fun populateFormGroups(formGroupView: RepeatingView) {
        val formGroupFirstName = FormGroupInputText(
            formGroupView.newChildId(),
            kModel.writableProperty(User::firstName),
            StringResourceModel("gitki.user.firstName")
        )
        formGroupFirstName.setRequired(true)
        formGroupFirstName.addAjaxValidation()
        formGroupView.add(formGroupFirstName)

        val formGroupLastName = FormGroupInputText(
            formGroupView.newChildId(),
            kModel.writableProperty(User::lastName),
            StringResourceModel("gitki.user.lastName")
        )
        formGroupLastName.setRequired(true)
        formGroupLastName.addAjaxValidation()
        formGroupView.add(formGroupLastName)

        val formGroupEmail = FormGroupInputEmail(
            formGroupView.newChildId(),
            kModel.writableProperty(User::email),
            Model.of("Email")
        )
        formGroupEmail.setRequired(true)
        formGroupEmail.addAjaxValidation()
        formGroupView.add(formGroupEmail)

        val formGroupLanguage = FormGroupSelect(
            formGroupView.newChildId(),
            kModel.writableProperty(User::language),
            StringResourceModel("gitki.user.language"),
            ListModel(Locale.getISOLanguages().toList()),
            IsoLanguageChoiceRenderer()
        )
        formGroupLanguage.setRequired(true)
        formGroupView.add(formGroupLanguage)

        val availableZoneIdsModel: IModel<List<String>> = ListModel(
            ZoneId.getAvailableZoneIds().toList()
                .sortedWith { obj: String, anotherString: String -> obj.compareTo(anotherString) }
        )
        val formGroupZoneId = FormGroupSelect(
            formGroupView.newChildId(),
            kModel.writableProperty(User::zoneId),
            StringResourceModel("gitki.timezone"),
            availableZoneIdsModel,
            ZoneIdChoiceRenderer()
        )
        formGroupZoneId.setRequired(true)
        formGroupView.add(formGroupZoneId)

        val formGroupRole = FormGroupSelect(
            formGroupView.newChildId(),
            kModel.writableProperty(User::role),
            StringResourceModel("gitki.user.role"),
            ListModel(Role.values().toList())
        )
        formGroupRole.setNullValid(false)
        formGroupRole.setRequired(true)
        formGroupRole.isVisible = getGitkiSession().hasRole(Role.ADMIN)
        formGroupView.add(formGroupRole)

        val formGroupPassword = FormGroupInputPassword(
            formGroupView.newChildId(),
            newPasswordModel,
            StringResourceModel("gitki.user.password")
        )
        formGroupPassword.setRequired(false)
        formGroupView.add(formGroupPassword)

        formGroupView.forEach { it.add(CssClassAppender(Spacing.MARGIN_BOTTOM_FULL)) }
    }

    override fun populateActions(buttonView: RepeatingView) {
        buttonView.add(SubmitLabelButton(buttonView.newChildId(), StringResourceModel("gitki.save")))
    }

    override fun onSubmit(target: AjaxRequestTarget?) {
        super.onSubmit(target)
        var user = this.kModelValue
        user = userService.save(user, newPasswordModel.getObject())

        /* Update user in session so changes are reflected e.g. for locale*/
        if (getCurrentUser()?.equals(user) == true) {
            getGitkiSession().signIn(user)
        }
        this.session.success(this.getString("gitki.user.saved"))
    }
}