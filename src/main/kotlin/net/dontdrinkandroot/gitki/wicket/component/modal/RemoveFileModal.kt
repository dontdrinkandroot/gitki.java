package net.dontdrinkandroot.gitki.wicket.component.modal

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.wicket.event.FileDeletedEvent
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.event.Broadcast
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.spring.injection.annot.SpringBean
import org.eclipse.jgit.api.errors.GitAPIException
import java.io.IOException

@Instantiate(Role.COMMITTER)
class RemoveFileModal(id: String, model: IModel<FilePath>) : AjaxFormModal<FilePath>(id, model) {

    @SpringBean
    private lateinit var gitService: GitService

    private var commitMessageModel: IModel<String> = Model.of("Removing " + this.modelObject.absoluteString)

    override fun createHeadingModel(): IModel<String> {
        return Model.of("Confirm File Removal")
    }

    override fun populateFormGroups(formGroupView: RepeatingView) {
        super.populateFormGroups(formGroupView)
        val formGroupCommitMessage =
            FormGroupInputText(formGroupView.newChildId(), commitMessageModel, Model.of("Commit Message"))
        formGroupCommitMessage.addAjaxValidation()
        formGroupCommitMessage.setRequired(true)
        formGroupView.add(formGroupCommitMessage)
    }

    override fun populateFormActions(formActionView: RepeatingView) {
        super.populateFormActions(formActionView)
        formActionView.add(SubmitLabelButton(formActionView.newChildId(), StringResourceModel("gitki.remove")))
        val cancelButton = Label(formActionView.newChildId(), "Cancel")
        cancelButton.add(ButtonBehavior())
        cancelButton.add(OnClickScriptBehavior(this.hideScript))
        formActionView.add(cancelButton)
    }

    protected fun onFileDeleted(target: AjaxRequestTarget?, path: FilePath?) {
        send(this.page, Broadcast.BREADTH, FileDeletedEvent(this.modelObject, target))
    }

    override fun onSubmit(target: AjaxRequestTarget?) {
        super.onSubmit(target)
        try {
            gitService.removeAndCommit(
                this@RemoveFileModal.modelObject!!,
                getGitkiSession().user!!,
                commitMessageModel.getObject()
            )
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        } catch (e: GitAPIException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun onAfterSubmit(target: AjaxRequestTarget?) {
        super.onAfterSubmit(target)
        onFileDeleted(target, this@RemoveFileModal.modelObject)
    }
}