package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputFile;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class UploadFilesModal extends AjaxFormModal<DirectoryPath>
{
    @SpringBean
    private GitService gitService;

    private IModel<List<FileUpload>> fileUploadsModel = new ListModel<>(new ArrayList<>());

    private IModel<String> commitMessageModel;

    public UploadFilesModal(String id, IModel<DirectoryPath> model)
    {
        super(id, model);
    }

    @Override
    protected IModel<String> createHeadingModel()
    {
        return Model.of("Upload Files");
    }

    @Override
    protected void populateFormGroups(RepeatingView formGroupView)
    {
        super.populateFormGroups(formGroupView);

        this.commitMessageModel = Model.of("Uploading files to " + this.getModelObject().toAbsoluteString());

        FormGroupInputFile formGroupFile =
                new FormGroupInputFile(formGroupView.newChildId(), Model.of("Files"), this.fileUploadsModel);
        formGroupFile.setMultiple(true);
        formGroupView.add(formGroupFile);

        FormGroupInputText formGroupCommitMessage =
                new FormGroupInputText(formGroupView.newChildId(), Model.of("Commit Message"), this.commitMessageModel);
        formGroupCommitMessage.setRequired(true);
        formGroupView.add(formGroupCommitMessage);
    }

    @Override
    protected void populateFormActions(RepeatingView formActionView)
    {
        super.populateFormActions(formActionView);

        formActionView.add(new SubmitLabelButton(
                formActionView.newChildId(),
                new StringResourceModel("gitki.files.upload")
        ));

        Label cancelButton = new Label(formActionView.newChildId(), "Cancel");
        cancelButton.add(new ButtonBehavior());
        cancelButton.add(new OnClickScriptBehavior(this.getHideScript()));
        formActionView.add(cancelButton);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target)
    {
        super.onSubmit(target);

        for (FileUpload fileUpload : UploadFilesModal.this.fileUploadsModel.getObject()) {
            FilePath path = UploadFilesModal.this.getModelObject()
                    .appendFileName(fileUpload.getClientFileName());
            try {
                UploadFilesModal.this.gitService.add(path, fileUpload.getBytes());
            } catch (IOException | GitAPIException e) {
                throw new WicketRuntimeException(e);
            }
        }
        try {
            UploadFilesModal.this.gitService.commit(
                    GitkiWebSession.get().getUser(),
                    UploadFilesModal.this.commitMessageModel.getObject()
            );
        } catch (GitAPIException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    protected void onAfterSubmit(AjaxRequestTarget target)
    {
        super.onAfterSubmit(target);

        this.setResponsePage(new DirectoryPage(UploadFilesModal.this.getModel()));
    }
}
