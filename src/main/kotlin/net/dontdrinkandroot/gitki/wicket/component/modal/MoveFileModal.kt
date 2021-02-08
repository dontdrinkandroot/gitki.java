package net.dontdrinkandroot.gitki.wicket.component.modal

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.wicket.choicerenderer.AbstractPathAbsoluteStringChoiceRenderer
import net.dontdrinkandroot.gitki.wicket.event.FileMovedEvent
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect
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
class MoveFileModal(id: String, model: IModel<FilePath>) : AjaxFormModal<FilePath>(id, model) {

    @SpringBean
    private val gitService: GitService? = null
    private val commitMessageModel: IModel<String>
    private val targetNameModel: IModel<String?>
    private val targetDirectoryModel: IModel<DirectoryPath?>
    override fun createHeadingModel(): IModel<String> {
        return StringResourceModel("gitki.move")
    }

    override fun populateFormGroups(formGroupView: RepeatingView) {
        super.populateFormGroups(formGroupView)
        var availableDirectories: List<DirectoryPath?>? = null
        availableDirectories = try {
            gitService!!.listAllDirectories()
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        }
        val formGroupTargetName = FormGroupInputText(
            formGroupView.newChildId(),
            StringResourceModel("gitki.name"),
            targetNameModel
        )
        formGroupTargetName.setRequired(true)
        formGroupView.add(formGroupTargetName)
        val formGroupTargetPath = FormGroupSelect(
            formGroupView.newChildId(),
            StringResourceModel("gitki.targetpath"),
            targetDirectoryModel,
            availableDirectories,
            AbstractPathAbsoluteStringChoiceRenderer<DirectoryPath?>()
        )
        formGroupTargetPath.setRequired(true)
        formGroupView.add(formGroupTargetPath)
        val formGroupCommitMessage = FormGroupInputText(
            formGroupView.newChildId(),
            StringResourceModel("gitki.commitmessage"),
            commitMessageModel
        )
        formGroupCommitMessage.addDefaultAjaxInputValidation()
        formGroupCommitMessage.setRequired(true)
        formGroupView.add(formGroupCommitMessage)
    }

    override fun populateFormActions(formActionView: RepeatingView) {
        super.populateFormActions(formActionView)
        formActionView.add(
            formActionView.add(
                SubmitLabelButton(
                    formActionView.newChildId(),
                    StringResourceModel("gitki.move")
                )
            )
        )
        val cancelButton = Label(formActionView.newChildId(), StringResourceModel("gitki.cancel"))
        cancelButton.add(ButtonBehavior())
        cancelButton.add(OnClickScriptBehavior(this.hideScript))
        formActionView.add(cancelButton)
    }

    protected val targetPath: FilePath
        protected get() = targetDirectoryModel.getObject()!!.appendFileName(targetNameModel.getObject()!!)

    protected fun onFileMoved(target: AjaxRequestTarget?, sourcePath: FilePath, targetPath: FilePath) {
        send(this.page, Broadcast.BREADTH, FileMovedEvent(sourcePath, target, targetPath))
    }

    override fun onSubmit(target: AjaxRequestTarget) {
        super.onSubmit(target)
        try {
            gitService!!.moveAndCommit(
                this@MoveFileModal.modelObject!!,
                targetPath,
                getGitkiSession().user!!,
                commitMessageModel.getObject()
            )
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        } catch (e: GitAPIException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun onAfterSubmit(target: AjaxRequestTarget) {
        super.onAfterSubmit(target)
        onFileMoved(target, this@MoveFileModal.modelObject, targetPath)
    }

    init {
        commitMessageModel = Model.of("Moving " + this.modelObject!!.absoluteString)
        targetNameModel = Model.of(this.modelObject!!.name)
        targetDirectoryModel = Model.of(this.modelObject!!.parent)
    }
}