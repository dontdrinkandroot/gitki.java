package net.dontdrinkandroot.gitki.wicket.component.modal

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.wicket.getCurrentUser
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputFile
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal
import net.dontdrinkandroot.wicket.kmodel.kModel
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.upload.FileUpload
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.model.util.ListModel
import org.apache.wicket.spring.injection.annot.SpringBean
import org.eclipse.jgit.api.errors.GitAPIException
import java.io.IOException
import java.util.*

@Instantiate(Role.COMMITTER)
class UploadFilesModal(id: String, model: IModel<DirectoryPath>) : AjaxFormModal<DirectoryPath>(id, model) {

    @SpringBean
    private lateinit var gitService: GitService

    private val fileUploadsModel: IModel<List<FileUpload>> = ListModel(ArrayList())

    private var commitMessageModel: IModel<String> = Model.of("Uploading files to " + this.modelObject!!.absoluteString)

    override fun createHeadingModel(): IModel<String> {
        return Model.of("Upload Files")
    }

    override fun populateFormGroups(formGroupView: RepeatingView) {
        super.populateFormGroups(formGroupView)
        val formGroupFile = FormGroupInputFile(formGroupView.newChildId(), fileUploadsModel, Model.of("Files"))
        formGroupFile.setMultiple(true)
        formGroupView.add(formGroupFile)
        val formGroupCommitMessage =
            FormGroupInputText(formGroupView.newChildId(), commitMessageModel, Model.of("Commit Message"))
        formGroupCommitMessage.setRequired(true)
        formGroupView.add(formGroupCommitMessage)
    }

    override fun populateFormActions(formActionView: RepeatingView) {
        super.populateFormActions(formActionView)
        formActionView.add(
            SubmitLabelButton(
                formActionView.newChildId(),
                StringResourceModel("gitki.files.upload")
            )
        )
        val cancelButton = Label(formActionView.newChildId(), "Cancel")
        cancelButton.add(ButtonBehavior())
        cancelButton.add(OnClickScriptBehavior(this.hideScript))
        formActionView.add(cancelButton)
    }

    override fun onSubmit(target: AjaxRequestTarget?) {
        super.onSubmit(target)
        for (fileUpload in fileUploadsModel.getObject()) {
            val path = this@UploadFilesModal.modelObject
                .appendFileName(fileUpload.clientFileName)
            try {
                gitService.add(path, fileUpload.bytes)
            } catch (e: IOException) {
                throw WicketRuntimeException(e)
            } catch (e: GitAPIException) {
                throw WicketRuntimeException(e)
            }
        }

        try {
            gitService.commit(getCurrentUser()!!, commitMessageModel.getObject())
        } catch (e: GitAPIException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun onAfterSubmit(target: AjaxRequestTarget?) {
        super.onAfterSubmit(target)
        this.setResponsePage(DirectoryPage(this@UploadFilesModal.kModel))
    }
}