package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.user.UserService;
import net.dontdrinkandroot.gitki.wicket.model.user.UserEmailModel;
import net.dontdrinkandroot.gitki.wicket.model.user.UserFirstNameModel;
import net.dontdrinkandroot.gitki.wicket.model.user.UserLastNameModel;
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.RepeatingAjaxForm;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputEmail;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputPassword;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import javax.servlet.http.HttpServletResponse;

public class FirstRunPage extends DecoratorPage<User>
{
    @SpringBean
    private UserService userService;

    private final IModel<String> passwordModel = new Model<>();

    public FirstRunPage(PageParameters parameters) {
        super(parameters);

        /* This page is only accessible when no admin user has been created */
        if (this.userService.hasAdminUser()) {
            throw new AbortWithHttpErrorCodeException(HttpServletResponse.SC_FORBIDDEN);
        }

        User admin = new User();
        admin.setRole(Role.ADMIN);
        this.setModel(Model.of(admin));
    }

    @Override
    protected IModel<String> createTitleModel() {
        return Model.of("Initial Configuration");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        RepeatingAjaxForm<Void> form = new RepeatingAjaxForm<Void>("form")
        {
            @Override
            protected void populateFormGroups(RepeatingView formGroupView) {
                FormGroupInputText formGroupFirstName = new FormGroupInputText(
                        formGroupView.newChildId(),
                        Model.of("First name"),
                        new UserFirstNameModel(FirstRunPage.this.getModel())
                );
                formGroupFirstName.setRequired(true);
                formGroupFirstName.addDefaultAjaxInputValidation();
                formGroupView.add(formGroupFirstName);

                FormGroupInputText formGroupLastName = new FormGroupInputText(
                        formGroupView.newChildId(),
                        Model.of("Last name"),
                        new UserLastNameModel(FirstRunPage.this.getModel())
                );
                formGroupLastName.setRequired(true);
                formGroupLastName.addDefaultAjaxInputValidation();
                formGroupView.add(formGroupLastName);

                FormGroupInputEmail formGroupEmail = new FormGroupInputEmail(
                        formGroupView.newChildId(),
                        Model.of("Email"),
                        new UserEmailModel(FirstRunPage.this.getModel())
                );
                formGroupEmail.setRequired(true);
                formGroupEmail.addDefaultAjaxInputValidation();
                formGroupView.add(formGroupEmail);

                FormGroupInputPassword formGroupPassword = new FormGroupInputPassword(
                        formGroupView.newChildId(),
                        Model.of("Password"),
                        FirstRunPage.this.passwordModel
                );
                formGroupPassword.setRequired(true);
                formGroupPassword.addDefaultAjaxInputValidation();
                formGroupView.add(formGroupPassword);
            }

            @Override
            protected void populateActions(RepeatingView buttonView) {
                buttonView.add(new SubmitLabelButton(buttonView.newChildId(), Model.of("Create")));
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                super.onSubmit(target);
                User admin = FirstRunPage.this.getModelObject();
                admin = FirstRunPage.this.userService.save(admin, FirstRunPage.this.passwordModel.getObject());
            }

            @Override
            protected void onAfterSubmit(AjaxRequestTarget target) {
                super.onAfterSubmit(target);
                FirstRunPage.this.setResponsePage(SignInPage.class);
            }
        };
        this.add(form);
    }
}
