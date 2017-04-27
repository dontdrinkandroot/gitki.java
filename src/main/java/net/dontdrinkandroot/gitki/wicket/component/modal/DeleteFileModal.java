package net.dontdrinkandroot.gitki.wicket.component.modal;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.GitService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.event.FileDeletedEvent;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.FormModal;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class DeleteFileModal extends FormModal<FilePath>
{
    @Inject
    private GitService gitService;

    private IModel<String> commitMessageModel;

    public DeleteFileModal(String id, IModel<FilePath> model)
    {
        super(id, model);
    }

    @Override
    protected IModel<String> createHeadingModel()
    {
        return Model.of("Confirm File Deletion");
    }

    @Override
    protected void populateFormGroups(RepeatingView formGroupView)
    {
        super.populateFormGroups(formGroupView);

        this.commitMessageModel = Model.of("Deleting " + this.getModelObject().toString());

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
                new AjaxSubmitButton(formActionView.newChildId(), this.getForm(), Model.of("Create"))
                {
                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form<?> form)
                    {
                        super.onSubmit(target, form);
                        try {
                            DeleteFileModal.this.gitService.deleteFile(
                                    DeleteFileModal.this.getModelObject().toPath(),
                                    GitkiWebSession.get().getUser(),
                                    DeleteFileModal.this.commitMessageModel.getObject()
                            );
                        } catch (IOException | GitAPIException e) {
                            throw new WicketRuntimeException(e);
                        }
                    }

                    @Override
                    protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
                    {
                        super.onAfterSubmit(target, form);
                        target.appendJavaScript(DeleteFileModal.this.getHideScript());
                        DeleteFileModal.this.onFileDeleted(target, DeleteFileModal.this.getModelObject());
                    }
                };
        formActionView.add(submitButton);

        Label cancelButton = new Label(formActionView.newChildId(), "Delete");
        cancelButton.add(new ButtonBehavior());
        cancelButton.add(new OnClickScriptBehavior(this.getHideScript()));
        formActionView.add(cancelButton);
    }

    protected void onFileDeleted(AjaxRequestTarget target, FilePath path)
    {
        this.send(this.getPage(), Broadcast.BREADTH, new FileDeletedEvent(this.getModelObject(), target));
    }
}
