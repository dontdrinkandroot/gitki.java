package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.choicerenderer.AbstractPathAbsoluteStringChoiceRenderer;
import net.dontdrinkandroot.gitki.wicket.event.DirectoryMovedEvent;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.FormModal;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
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
@Instantiate(Role.COMMITTER)
public class MoveDirectoryModal extends FormModal<DirectoryPath>
{
    @SpringBean
    private GitService gitService;

    private IModel<String> commitMessageModel;

    private IModel<DirectoryPath> targetDirectoryModel;

    private IModel<String> targetNameModel;

    public MoveDirectoryModal(String id, IModel<DirectoryPath> model)
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

        List<DirectoryPath> availableDirectories;
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

        AjaxSubmitButton submitButton =
                new AjaxSubmitButton(formActionView.newChildId(), this.getForm(), new StringResourceModel("gitki.move"))
                {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
                    {
                        super.onSubmit(target, form);
                        try {
                            MoveDirectoryModal.this.gitService.moveAndCommit(
                                    MoveDirectoryModal.this.getModelObject(),
                                    MoveDirectoryModal.this.getTargetPath(),
                                    GitkiWebSession.get().getUser(),
                                    MoveDirectoryModal.this.commitMessageModel.getObject()
                            );
                        } catch (IOException | GitAPIException e) {
                            throw new WicketRuntimeException(e);
                        }
                    }

                    @Override
                    protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
                    {
                        super.onAfterSubmit(target, form);
                        target.appendJavaScript(MoveDirectoryModal.this.getHideScript());
                        MoveDirectoryModal.this.onDirectoryMoved(target, MoveDirectoryModal.this.getModelObject(),
                                MoveDirectoryModal.this.getTargetPath()
                        );
                    }
                };
        formActionView.add(submitButton);

        Label cancelButton = new Label(formActionView.newChildId(), new StringResourceModel("gitki.cancel"));
        cancelButton.add(new ButtonBehavior());
        cancelButton.add(new OnClickScriptBehavior(this.getHideScript()));
        formActionView.add(cancelButton);
    }

    protected DirectoryPath getTargetPath()
    {
        return this.targetDirectoryModel.getObject().appendDirectoryName(this.targetNameModel.getObject());
    }

    protected void onDirectoryMoved(AjaxRequestTarget target, DirectoryPath sourcePath, DirectoryPath targetPath)
    {
        this.send(this.getPage(), Broadcast.BREADTH, new DirectoryMovedEvent(sourcePath, target, targetPath));
    }
}
