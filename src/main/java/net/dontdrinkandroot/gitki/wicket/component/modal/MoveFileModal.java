package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.choicerenderer.AbstractPathAbsoluteStringChoiceRenderer;
import net.dontdrinkandroot.gitki.wicket.event.FileMovedEvent;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class MoveFileModal extends AjaxFormModal<FilePath>
{
    @SpringBean
    private GitService gitService;

    private IModel<String> commitMessageModel;

    private IModel<String> targetNameModel;

    private IModel<DirectoryPath> targetDirectoryModel;

    public MoveFileModal(String id, IModel<FilePath> model)
    {
        super(id, model);
        this.commitMessageModel = Model.of("Moving " + this.getModelObject().toAbsoluteString());
        this.targetNameModel = Model.of(this.getModelObject().getName());
        this.targetDirectoryModel = Model.of(this.getModelObject().getParent());
    }

    @Override
    protected IModel<String> createHeadingModel()
    {
        return new StringResourceModel("gitki.move");
    }

    @Override
    protected void populateFormGroups(RepeatingView formGroupView)
    {
        super.populateFormGroups(formGroupView);

        List<DirectoryPath> availableDirectories = null;
        try {
            availableDirectories = this.gitService.listAllDirectories();
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }

        FormGroupInputText formGroupTargetName =
                new FormGroupInputText(
                        formGroupView.newChildId(),
                        new StringResourceModel("gitki.name"),
                        this.targetNameModel
                );
        formGroupTargetName.setRequired(true);
        formGroupView.add(formGroupTargetName);

        FormGroupSelect<DirectoryPath> formGroupTargetPath =
                new FormGroupSelect<>(
                        formGroupView.newChildId(),
                        new StringResourceModel("gitki.targetpath"),
                        this.targetDirectoryModel,
                        availableDirectories,
                        new AbstractPathAbsoluteStringChoiceRenderer<>()
                );
        formGroupTargetPath.setRequired(true);
        formGroupView.add(formGroupTargetPath);

        FormGroupInputText formGroupCommitMessage =
                new FormGroupInputText(
                        formGroupView.newChildId(),
                        new StringResourceModel("gitki.commitmessage"),
                        this.commitMessageModel
                );
        formGroupCommitMessage.addDefaultAjaxInputValidation();
        formGroupCommitMessage.setRequired(true);
        formGroupView.add(formGroupCommitMessage);
    }

    @Override
    protected void populateFormActions(RepeatingView formActionView)
    {
        super.populateFormActions(formActionView);

        formActionView.add(formActionView.add(new SubmitLabelButton(
                formActionView.newChildId(),
                new StringResourceModel("gitki.move")
        )));

        Label cancelButton = new Label(formActionView.newChildId(), new StringResourceModel("gitki.cancel"));
        cancelButton.add(new ButtonBehavior());
        cancelButton.add(new OnClickScriptBehavior(this.getHideScript()));
        formActionView.add(cancelButton);
    }

    protected FilePath getTargetPath()
    {
        return this.targetDirectoryModel.getObject().appendFileName(this.targetNameModel.getObject());
    }

    protected void onFileMoved(AjaxRequestTarget target, FilePath sourcePath, FilePath targetPath)
    {
        this.send(this.getPage(), Broadcast.BREADTH, new FileMovedEvent(sourcePath, target, targetPath));
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target)
    {
        super.onSubmit(target);

        try {
            MoveFileModal.this.gitService.moveAndCommit(
                    MoveFileModal.this.getModelObject(),
                    MoveFileModal.this.getTargetPath(),
                    GitkiWebSession.get().getUser(),
                    MoveFileModal.this.commitMessageModel.getObject()
            );
        } catch (IOException | GitAPIException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    protected void onAfterSubmit(AjaxRequestTarget target)
    {
        super.onAfterSubmit(target);

        MoveFileModal.this.onFileMoved(target, MoveFileModal.this.getModelObject(), MoveFileModal.this.getTargetPath());
    }
}
