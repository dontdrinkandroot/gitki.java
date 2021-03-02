package net.dontdrinkandroot.gitki.wicket.component.modal

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.wicket.choicerenderer.AbstractPathAbsoluteStringChoiceRenderer
import net.dontdrinkandroot.gitki.wicket.event.DirectoryMovedEvent
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal
import net.dontdrinkandroot.wicket.model.ldm
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
class MoveDirectoryModal(id: String, model: IModel<DirectoryPath>) : AjaxFormModal<DirectoryPath>(id, model) {

    @SpringBean
    private lateinit var gitService: GitService

    private val commitMessageModel: IModel<String>

    private val targetDirectoryModel: IModel<DirectoryPath>

    private val targetNameModel: IModel<String?>

    override fun createHeadingModel(): IModel<String> {
        return StringResourceModel("gitki.move")
    }

    override fun populateFormGroups(formGroupView: RepeatingView) {
        super.populateFormGroups(formGroupView)
        val availableDirectoriesModel = { gitService.listAllDirectories() }.ldm()
        val formGroupTargetName = FormGroupInputText(
            formGroupView.newChildId(),
            targetNameModel,
            StringResourceModel("gitki.name")
        )
        formGroupTargetName.setRequired(true)
        formGroupView.add(formGroupTargetName)
        val formGroupTargetPath = FormGroupSelect(
            formGroupView.newChildId(),
            targetDirectoryModel,
            StringResourceModel("gitki.targetpath"),
            availableDirectoriesModel,
            AbstractPathAbsoluteStringChoiceRenderer()
        )
        formGroupTargetPath.setRequired(true)
        formGroupView.add(formGroupTargetPath)
        val formGroupCommitMessage = FormGroupInputText(
            formGroupView.newChildId(),
            StringResourceModel("gitki.commitmessage"),
            commitMessageModel
        )
        formGroupCommitMessage.addAjaxValidation()
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

    protected val targetPath: DirectoryPath
        protected get() = targetDirectoryModel.getObject()!!.appendDirectoryName(targetNameModel.getObject()!!)

    protected fun onDirectoryMoved(target: AjaxRequestTarget?, sourcePath: DirectoryPath, targetPath: DirectoryPath) {
        send(this.page, Broadcast.BREADTH, DirectoryMovedEvent(sourcePath, target, targetPath))
    }

    override fun onSubmit(target: AjaxRequestTarget?) {
        super.onSubmit(target)
        try {
            gitService.moveAndCommit(
                this@MoveDirectoryModal.modelObject!!,
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

    override fun onAfterSubmit(target: AjaxRequestTarget?) {
        super.onAfterSubmit(target)
        onDirectoryMoved(
            target,
            this@MoveDirectoryModal.modelObject,
            targetPath
        )
    }

    init {
        commitMessageModel = Model.of("Moving " + this.modelObject!!.absoluteString)
        targetNameModel = Model.of(this.modelObject!!.name)
        targetDirectoryModel = Model.of(this.modelObject!!.parent)
    }
}