package net.dontdrinkandroot.gitki.wicket.form;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.user.UserService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.choicerenderer.IsoLanguageChoiceRenderer;
import net.dontdrinkandroot.gitki.wicket.choicerenderer.ZoneIdChoiceRenderer;
import net.dontdrinkandroot.gitki.wicket.page.admin.UserListPage;
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.RepeatingAjaxForm;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputEmail;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputPassword;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserEditForm extends RepeatingAjaxForm<User>
{
    @SpringBean
    private UserService userService;

    private IModel<String> newPasswordModel = new Model<>();

    public UserEditForm(String id, IModel<User> model)
    {
        super(id, model);
    }

    @Override
    protected void populateFormGroups(RepeatingView formGroupView)
    {
        FormGroupInputText formGroupFirstName = new FormGroupInputText(
                formGroupView.newChildId(),
                Model.of("First Name"),
                new PropertyModel<>(this.getModel(), "firstName")
        );
        formGroupFirstName.setRequired(true);
        formGroupFirstName.addDefaultAjaxInputValidation();
        formGroupView.add(formGroupFirstName);

        FormGroupInputText formGroupLastName = new FormGroupInputText(
                formGroupView.newChildId(),
                Model.of("Last Name"),
                new PropertyModel<>(this.getModel(), "lastName")
        );
        formGroupLastName.setRequired(true);
        formGroupLastName.addDefaultAjaxInputValidation();
        formGroupView.add(formGroupLastName);

        FormGroupInputEmail formGroupEmail =
                new FormGroupInputEmail(
                        formGroupView.newChildId(),
                        Model.of("Email"),
                        new PropertyModel<>(this.getModel(), "email")
                );
        formGroupEmail.setRequired(true);
        formGroupEmail.addDefaultAjaxInputValidation();
        formGroupView.add(formGroupEmail);

        FormGroupSelect<String> formGroupLanguage = new FormGroupSelect<>(
                formGroupView.newChildId(),
                Model.of("Language"),
                new PropertyModel<>(this.getModel(), "language"),
                Arrays.asList(Locale.getISOLanguages()),
                new IsoLanguageChoiceRenderer()
        );
        formGroupLanguage.setRequired(true);
        formGroupView.add(formGroupLanguage);

        List<String> availableZoneIds = new ArrayList<>(ZoneId.getAvailableZoneIds());
        availableZoneIds.sort(String::compareTo);
        FormGroupSelect<String> formGroupZoneId =
                new FormGroupSelect<>(
                        formGroupView.newChildId(),
                        new StringResourceModel("gitki.timezone"),
                        new PropertyModel<>(this.getModel(), "zoneId"),
                        availableZoneIds,
                        new ZoneIdChoiceRenderer()
                );
        formGroupZoneId.setRequired(true);
        formGroupView.add(formGroupZoneId);

        FormGroupSelect<Role> formGroupRole =
                new FormGroupSelect<>(
                        formGroupView.newChildId(),
                        Model.of("Role"),
                        new PropertyModel<>(this.getModel(), "role"),
                        Arrays.asList(Role.values())
                );
        formGroupRole.setNullValid(false);
        formGroupRole.setRequired(true);
        formGroupRole.setVisible(GitkiWebSession.get().hasRole(Role.ADMIN));
        formGroupView.add(formGroupRole);

        FormGroupInputPassword formGroupPassword =
                new FormGroupInputPassword(formGroupView.newChildId(), Model.of("Password"), this.newPasswordModel);
        formGroupPassword.setRequired(false);
        formGroupView.add(formGroupPassword);
    }

    @Override
    protected void populateActions(RepeatingView buttonView)
    {
        buttonView.add(new SubmitLabelButton(buttonView.newChildId(), new StringResourceModel("gitki.save")));
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target)
    {
        super.onSubmit(target);
        User user = this.getModelObject();
        user = this.userService.save(user, this.newPasswordModel.getObject());

        /* Update user in session so changes are reflected e.g. for locale*/
        if (GitkiWebSession.get().getUser().equals(user)) {
            GitkiWebSession.get().signIn(user);
        }
    }

    @Override
    protected void onAfterSubmit(AjaxRequestTarget target)
    {
        super.onAfterSubmit(target);
        this.setResponsePage(UserListPage.class);
    }
}
