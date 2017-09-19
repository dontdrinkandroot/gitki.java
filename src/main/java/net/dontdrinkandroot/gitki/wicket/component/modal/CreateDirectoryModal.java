package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class CreateDirectoryModal extends AjaxFormModal<DirectoryPath>
{
    @SpringBean
    private GitService gitService;

    private IModel<String> nameModel;

    public CreateDirectoryModal(String id, IModel<DirectoryPath> model)
    {
        super(id, model);
    }

    @Override
    protected IModel<String> createHeadingModel()
    {
        return new StringResourceModel("gitki.directory.create");
    }

    @Override
    protected void populateFormGroups(RepeatingView formGroupView)
    {
        super.populateFormGroups(formGroupView);

        this.nameModel = new Model<>();

        FormGroupInputText formGroupName =
                new FormGroupInputText(
                        formGroupView.newChildId(),
                        new StringResourceModel("gitki.name"),
                        this.nameModel
                );
        formGroupName.addDefaultAjaxInputValidation();
        formGroupName.setRequired(true);
        formGroupView.add(formGroupName);
    }

    @Override
    protected void populateFormActions(RepeatingView formActionView)
    {
        super.populateFormActions(formActionView);

        formActionView.add(new SubmitLabelButton(formActionView.newChildId(), new StringResourceModel("gitki.create")));

        Label cancelButton = new Label(formActionView.newChildId(), new StringResourceModel("gitki.cancel"));
        cancelButton.add(new ButtonBehavior());
        cancelButton.add(new OnClickScriptBehavior(this.getHideScript()));
        formActionView.add(cancelButton);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target)
    {
        super.onSubmit(target);

        try {
            CreateDirectoryModal.this.gitService.createDirectory(this.getNewPath());
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    protected void onAfterSubmit(AjaxRequestTarget target)
    {
        super.onAfterSubmit(target);

        this.setResponsePage(new DirectoryPage(Model.of(this.getNewPath())));
    }

    private DirectoryPath getNewPath()
    {
        return CreateDirectoryModal.this.getModelObject()
                .appendDirectoryName(CreateDirectoryModal.this.nameModel.getObject());
    }
}
