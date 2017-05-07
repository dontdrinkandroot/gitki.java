package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.FileType;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.MarkdownEditPage;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.TextEditPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.FormModal;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Arrays;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class CreateFileModal extends FormModal<DirectoryPath>
{
    private IModel<String> nameModel;

    private IModel<FileType> fileTypeModel;

    public CreateFileModal(String id, IModel<DirectoryPath> model)
    {
        super(id, model);
    }

    @Override
    protected IModel<String> createHeadingModel()
    {
        return Model.of("Create File");
    }

    @Override
    protected void populateFormGroups(RepeatingView formGroupView)
    {
        super.populateFormGroups(formGroupView);

        this.nameModel = new Model<>();
        this.fileTypeModel = new Model<>();

        FormGroupInputText formGroupName =
                new FormGroupInputText(formGroupView.newChildId(), Model.of("Name"), this.nameModel);
        formGroupName.addDefaultAjaxInputValidation();
        formGroupName.setRequired(true);
        formGroupView.add(formGroupName);

        FormGroupSelect<FileType> formGroupFileType =
                new FormGroupSelect<FileType>(formGroupView.newChildId(), Model.of("Type"), this.fileTypeModel,
                        Arrays.asList(FileType.values())
                );
        formGroupFileType.addAjaxValidation("change");
        formGroupFileType.setRequired(true);
        formGroupFileType.setNullValid(false);
        formGroupView.add(formGroupFileType);
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

                        FileType fileType = CreateFileModal.this.fileTypeModel.getObject();
                        String fullName = CreateFileModal.this.nameModel.getObject() + "." + fileType.getExtension();
                        FilePath filePath = CreateFileModal.this.getModelObject().appendFileName(fullName);
                        switch (fileType) {
                            case MARKDOWN:
                                this.setResponsePage(new MarkdownEditPage(Model.of(filePath)));
                                break;
                            case TEXT:
                                this.setResponsePage(new TextEditPage(Model.of(filePath)));
                                break;
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
