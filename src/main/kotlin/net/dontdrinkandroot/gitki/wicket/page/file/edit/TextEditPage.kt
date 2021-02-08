package net.dontdrinkandroot.gitki.wicket.page.file.edit

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.service.lock.LockedException
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupTextArea
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.eclipse.jgit.api.errors.GitAPIException
import java.io.FileNotFoundException
import java.io.IOException

class TextEditPage : EditPage {

    private lateinit var contentModel: IModel<String>

    private lateinit var commitMessageModel: IModel<String>

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<FilePath>) : super(model)

    override fun createTitleModel(): IModel<String> {
        return AbstractPathNameModel(this.model)
    }

    override fun onInitialize() {
        super.onInitialize()
        try {
            contentModel = Model.of(this.gitService.getContentAsString(this.modelObject))
        } catch (e: FileNotFoundException) {
            contentModel = Model.of("")
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        }
        commitMessageModel = Model.of("Editing " + this.modelObject.absoluteString)
        val editForm = Form("editForm", contentModel)
        this.add(editForm)
        val formGroupContent = FormGroupTextArea("content", Model.of("Content"), contentModel)
        formGroupContent.setRows(20)
        editForm.add(formGroupContent)
        val formGroupCommitMessage = FormGroupInputText("commitMessage", Model.of("Commit message"), commitMessageModel)
        formGroupCommitMessage.setRequired(true)
        editForm.add(formGroupCommitMessage)
        val submitButton: AjaxSubmitButton = object : AjaxSubmitButton("submit", Model.of("Save")) {
            override fun onSubmit(target: AjaxRequestTarget) {
                super.onSubmit(target)
                try {
                    wikiService
                        .saveAndUnlock(
                            this@TextEditPage.modelObject,
                            getGitkiSession().user!!,
                            commitMessageModel.getObject(),
                            contentModel.getObject()
                        )
                } catch (e: IOException) {
                    throw WicketRuntimeException(e)
                } catch (e: GitAPIException) {
                    throw WicketRuntimeException(e)
                } catch (e: LockedException) {
                    throw WicketRuntimeException(e)
                }
            }

            override fun onAfterSubmit(target: AjaxRequestTarget) {
                super.onAfterSubmit(target)
                this.setResponsePage(
                    SimpleViewPage::class.java,
                    PageParameterUtils.from(this@TextEditPage.modelObject)
                )
            }
        }
        editForm.add(submitButton)
    }
}