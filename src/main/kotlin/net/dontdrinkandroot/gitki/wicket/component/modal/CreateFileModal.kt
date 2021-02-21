package net.dontdrinkandroot.gitki.wicket.component.modal

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.FileType
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.choicerenderer.FileTypeChoiceRenderer
import net.dontdrinkandroot.gitki.wicket.component.button.ModalCancelButton
import net.dontdrinkandroot.gitki.wicket.page.file.edit.MarkdownEditPage
import net.dontdrinkandroot.gitki.wicket.page.file.edit.TextEditPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupSelect
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.model.util.ListModel
import java.util.*

@Instantiate(Role.COMMITTER)
class CreateFileModal(id: String, model: IModel<DirectoryPath>) : AjaxFormModal<DirectoryPath>(id, model) {

    private val nameModel: IModel<String> = Model()

    private val fileTypeModel: IModel<FileType> = Model.of(FileType.MARKDOWN)

    override fun createHeadingModel(): IModel<String> {
        return StringResourceModel("gitki.file.create")
    }

    override fun populateFormGroups(formGroupView: RepeatingView) {
        super.populateFormGroups(formGroupView)
        val formGroupName = FormGroupInputText(
            formGroupView.newChildId(),
            StringResourceModel("gitki.name"),
            nameModel
        )
        formGroupName.addAjaxValidation()
        formGroupName.setRequired(true)
        formGroupView.add(formGroupName)
        val formGroupFileType = FormGroupSelect(
            formGroupView.newChildId(),
            fileTypeModel,
            StringResourceModel("gitki.file.type"),
            ListModel(Arrays.asList(*FileType.values())),
            FileTypeChoiceRenderer()
        )
        formGroupFileType.addAjaxValidation("change")
        formGroupFileType.setRequired(true)
        formGroupFileType.setNullValid(false)
        formGroupView.add(formGroupFileType)
    }

    override fun populateFormActions(formActionView: RepeatingView) {
        super.populateFormActions(formActionView)
        formActionView.add(SubmitLabelButton(formActionView.newChildId(), StringResourceModel("gitki.create")))
        formActionView.add(ModalCancelButton(formActionView.newChildId(), this))
    }

    override fun onAfterSubmit(target: AjaxRequestTarget?) {
        super.onAfterSubmit(target)
        val fileType = fileTypeModel.getObject()
        val fullName = nameModel.getObject().toString() + "." + fileType.extension
        val filePath = this.modelObject!!.appendFileName(fullName)
        when (fileType) {
            FileType.MARKDOWN -> this.setResponsePage(MarkdownEditPage(Model.of(filePath)))
            FileType.TEXT -> this.setResponsePage(TextEditPage(Model.of(filePath)))
        }
    }
}