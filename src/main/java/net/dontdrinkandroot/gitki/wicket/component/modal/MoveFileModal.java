package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.event.FileMovedEvent;
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.FormModal;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
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
@AuthorizeInstantiation(Role.Constants.COMMITTER)
public class MoveFileModal extends FormModal<FilePath>
{
    @SpringBean
    private GitService gitService;

    private IModel<String> commitMessageModel;

    private IModel<DirectoryPath> targetPathModel;

    public MoveFileModal(String id, IModel<FilePath> model)
    {
        super(id, model);
        this.commitMessageModel = Model.of("Moving " + this.getModelObject().toAbsoluteString());
        this.targetPathModel = Model.of(this.getModelObject().getParent());
    }

    @Override
    protected IModel<String> createHeadingModel()
    {
        return Model.of("Move File");
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

        FormGroupSelect<DirectoryPath> formGroupTargetPath =
                new FormGroupSelect<>(
                        formGroupView.newChildId(),
                        new StringResourceModel("targetpath"),
                        this.targetPathModel,
                        availableDirectories
                );
        formGroupTargetPath.setRequired(true);
        formGroupView.add(formGroupTargetPath);

        FormGroupInputText formGroupCommitMessage =
                new FormGroupInputText(formGroupView.newChildId(), Model.of("Commit Message"), this.commitMessageModel);
        formGroupCommitMessage.addDefaultAjaxInputValidation();
        formGroupCommitMessage.setRequired(true);
        formGroupView.add(formGroupCommitMessage);
    }

    @Override
    protected void populateFormActions(RepeatingView formActionView)
    {
        super.populateFormActions(formActionView);

        AjaxSubmitButton submitButton =
                new AjaxSubmitButton(formActionView.newChildId(), this.getForm(), Model.of("Move"))
                {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
                    {
                        super.onSubmit(target, form);
                        try {
                            MoveFileModal.this.gitService.moveAndCommit(
                                    MoveFileModal.this.getModelObject(),
                                    MoveFileModal.this.targetPathModel.getObject(),
                                    GitkiWebSession.get().getUser(),
                                    MoveFileModal.this.commitMessageModel.getObject()
                            );
                        } catch (IOException | GitAPIException e) {
                            throw new WicketRuntimeException(e);
                        }
                    }

                    @Override
                    protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
                    {
                        super.onAfterSubmit(target, form);
                        target.appendJavaScript(MoveFileModal.this.getHideScript());
                        MoveFileModal.this.onFileMoved(target, MoveFileModal.this.getModelObject());
                    }
                };
        formActionView.add(submitButton);

        Label cancelButton = new Label(formActionView.newChildId(), "Cancel");
        cancelButton.add(new ButtonBehavior());
        cancelButton.add(new OnClickScriptBehavior(this.getHideScript()));
        formActionView.add(cancelButton);
    }

    protected void onFileMoved(AjaxRequestTarget target, FilePath path)
    {
        this.send(this.getPage(), Broadcast.BREADTH, new FileMovedEvent(this.getModelObject(), target));
    }
}
