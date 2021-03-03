package net.dontdrinkandroot.gitki.wicket.component.modal

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.wicket.component.button.ModalCancelButton
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.bootstrap.component.button.SubmitLabelButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.modal.AjaxFormModal
import net.dontdrinkandroot.wicket.kmodel.ValueKModel
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.spring.injection.annot.SpringBean
import java.io.IOException

@Instantiate(Role.COMMITTER)
class CreateDirectoryModal(id: String, model: IModel<DirectoryPath>) : AjaxFormModal<DirectoryPath>(id, model) {

    @SpringBean
    private lateinit var gitService: GitService

    private var nameModel: IModel<String?> = Model()

    override fun createHeadingModel(): IModel<String> {
        return StringResourceModel("gitki.directory.create")
    }

    override fun populateFormGroups(formGroupView: RepeatingView) {
        super.populateFormGroups(formGroupView)
        val formGroupName = FormGroupInputText(
            formGroupView.newChildId(),
            nameModel,
            StringResourceModel("gitki.name"),
        )
        formGroupName.addAjaxValidation()
        formGroupName.setRequired(true)
        formGroupView.add(formGroupName)
    }

    override fun populateFormActions(formActionView: RepeatingView) {
        super.populateFormActions(formActionView)
        formActionView.add(SubmitLabelButton(formActionView.newChildId(), StringResourceModel("gitki.create")))
        formActionView.add(ModalCancelButton(formActionView.newChildId(), this))
    }

    override fun onSubmit(target: AjaxRequestTarget?) {
        super.onSubmit(target)
        try {
            gitService.createDirectory(newPath)
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun onAfterSubmit(target: AjaxRequestTarget?) {
        super.onAfterSubmit(target)
        this.setResponsePage(DirectoryPage(ValueKModel(newPath)))
    }

    private val newPath: DirectoryPath
        private get() = this@CreateDirectoryModal.modelObject.appendDirectoryName(nameModel.getObject()!!)
}