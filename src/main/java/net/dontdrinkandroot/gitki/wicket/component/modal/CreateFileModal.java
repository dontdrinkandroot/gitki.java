package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.FileType;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.choicerenderer.FileTypeChoiceRenderer;
import net.dontdrinkandroot.gitki.wicket.component.button.ModalCancelButton;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.MarkdownEditPage;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.TextEditPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;

import java.util.Arrays;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class CreateFileModal extends AjaxFormModal<DirectoryPath>
{
    private IModel<String> nameModel = new Model<>();

    private IModel<FileType> fileTypeModel = Model.of(FileType.MARKDOWN);

    public CreateFileModal(String id, IModel<DirectoryPath> model)
    {
        super(id, model);
    }

    @Override
    protected IModel<String> createHeadingModel()
    {
        return new StringResourceModel("gitki.file.create");
    }

    @Override
    protected void populateFormGroups(RepeatingView formGroupView)
    {
        super.populateFormGroups(formGroupView);

        FormGroupInputText formGroupName = new FormGroupInputText(
                formGroupView.newChildId(),
                new StringResourceModel("gitki.name"),
                this.nameModel
        );
        formGroupName.addDefaultAjaxInputValidation();
        formGroupName.setRequired(true);
        formGroupView.add(formGroupName);

        FormGroupSelect<FileType> formGroupFileType = new FormGroupSelect<>(
                formGroupView.newChildId(),
                new StringResourceModel("gitki.file.type"),
                this.fileTypeModel,
                Arrays.asList(FileType.values()),
                new FileTypeChoiceRenderer()
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

        formActionView.add(new SubmitLabelButton(formActionView.newChildId(), new StringResourceModel("gitki.create")));
        formActionView.add(new ModalCancelButton(formActionView.newChildId(), this));
    }

    @Override
    protected void onAfterSubmit(AjaxRequestTarget target)
    {
        super.onAfterSubmit(target);
        FileType fileType = this.fileTypeModel.getObject();
        String fullName = this.nameModel.getObject() + "." + fileType.getExtension();
        FilePath filePath = this.getModelObject().appendFileName(fullName);
        switch (fileType) {
            case MARKDOWN:
                this.setResponsePage(new MarkdownEditPage(Model.of(filePath)));
                break;
            case TEXT:
                this.setResponsePage(new TextEditPage(Model.of(filePath)));
                break;
        }
    }
}
