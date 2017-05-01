package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.FormModal;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class CreateDirectoryModal extends FormModal<DirectoryPath>
{
    @Inject
    private GitService gitService;

    private IModel<String> nameModel;

    public CreateDirectoryModal(String id, IModel<DirectoryPath> model)
    {
        super(id, model);
    }

    @Override
    protected IModel<String> createHeadingModel()
    {
        return Model.of("Create Directory");
    }

    @Override
    protected void populateFormGroups(RepeatingView formGroupView)
    {
        super.populateFormGroups(formGroupView);

        this.nameModel = new Model<>();

        FormGroupInputText formGroupName =
                new FormGroupInputText(formGroupView.newChildId(), Model.of("Name"), this.nameModel);
        formGroupName.addDefaultAjaxInputValidation();
        formGroupName.setRequired(true);
        formGroupView.add(formGroupName);
    }

    @Override
    protected void populateFormActions(RepeatingView formActionView)
    {
        super.populateFormActions(formActionView);

        AjaxSubmitButton submitButton =
                new AjaxSubmitButton(formActionView.newChildId(), this.getForm(), Model.of("Create"))
                {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
                    {
                        super.onSubmit(target, form);
                    }

                    @Override
                    protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
                    {
                        super.onAfterSubmit(target, form);

                        DirectoryPath newPath =
                                CreateDirectoryModal.this.getModelObject()
                                        .appendDirectoryName(CreateDirectoryModal.this.nameModel
                                                .getObject());
                        try {
                            CreateDirectoryModal.this.gitService.createDirectory(newPath.toPath());
                            this.setResponsePage(new DirectoryPage(Model.of(newPath)));
                        } catch (IOException e) {
                            throw new WicketRuntimeException(e);
                        }
                    }
                };
        formActionView.add(submitButton);

        Label cancelButton = new Label(formActionView.newChildId(), "Cancel");
        cancelButton.add(new ButtonBehavior());
        cancelButton.add(new OnClickScriptBehavior(this.getHideScript()));
        formActionView.add(cancelButton);
    }
}
