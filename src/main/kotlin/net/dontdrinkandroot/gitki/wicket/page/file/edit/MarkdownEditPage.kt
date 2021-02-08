package net.dontdrinkandroot.gitki.wicket.page.file.edit

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.service.lock.LockMissingException
import net.dontdrinkandroot.gitki.service.lock.LockedException
import net.dontdrinkandroot.gitki.service.markdown.MarkdownService
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton
import net.dontdrinkandroot.wicket.bootstrap.component.button.Button
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupTextArea
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes
import org.apache.wicket.ajax.attributes.ThrottlingSettings
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean
import org.eclipse.jgit.api.errors.GitAPIException
import java.io.FileNotFoundException
import java.io.IOException
import java.time.Duration
import java.util.*

class MarkdownEditPage : EditPage {

    @SpringBean
    private lateinit var markdownService: MarkdownService

    private lateinit var contentModel: IModel<String>

    private lateinit var commitMessageModel: IModel<String>

    private var formGroupContent: FormGroupTextArea<String>? = null

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<FilePath>) : super(model)

    override fun onInitialize() {
        super.onInitialize()
        try {
            contentModel = Model.of(this.gitService.getContentAsString(this.modelObject))
        } catch (e: FileNotFoundException) {
            contentModel = Model.of(createDefaultContent())
        } catch (e: IOException) {
            throw WicketRuntimeException(e)
        }
        commitMessageModel = Model.of("Editing " + this.modelObject.absoluteString)
        val preview = Label("preview") { markdownService.parseToHtml(contentModel.getObject()) }
        preview.escapeModelStrings = false
        preview.outputMarkupId = true
        this.add(preview)
        val editForm = Form("editForm", contentModel)
        this.add(editForm)
        formGroupContent = FormGroupTextArea("content", StringResourceModel("gitki.content"), contentModel)
        formGroupContent!!.setRows(20)
        formGroupContent!!.formComponent.add(object : AjaxFormComponentUpdatingBehavior("input") {
            override fun updateAjaxAttributes(attributes: AjaxRequestAttributes) {
                super.updateAjaxAttributes(attributes)
                attributes.throttlingSettings = ThrottlingSettings(
                    "markdownpreview",
                    Duration.ofMillis(250),
                    true
                )
            }

            override fun onUpdate(target: AjaxRequestTarget) {
                target.add(preview)
            }
        })
        editForm.add(formGroupContent)
        val formGroupCommitMessage = FormGroupInputText(
            "commitMessage",
            StringResourceModel("gitki.commitmessage"),
            commitMessageModel
        )
        formGroupCommitMessage.setRequired(true)
        editForm.add(formGroupCommitMessage)
        val saveAndBackButton: AjaxSubmitButton =
            object : AjaxSubmitButton("saveandback", StringResourceModel("gitki.saveandback")) {
                override fun onSubmit(target: AjaxRequestTarget) {
                    try {
                        wikiService
                            .saveAndUnlock(
                                this@MarkdownEditPage.modelObject,
                                getGitkiSession().user!!,
                                commitMessageModel.getObject(),
                                contentModel.getObject()
                            )
                    } catch (e: LockedException) {
                        throw WicketRuntimeException(e)
                    } catch (e: GitAPIException) {
                        throw WicketRuntimeException(e)
                    } catch (e: IOException) {
                        throw WicketRuntimeException(e)
                    }
                }

                override fun onAfterSubmit(target: AjaxRequestTarget) {
                    super.onAfterSubmit(target)
                    this.setResponsePage(
                        SimpleViewPage::class.java,
                        PageParameterUtils.from(this@MarkdownEditPage.modelObject)
                    )
                }
            }
        editForm.add(saveAndBackButton)
        val saveButton: AjaxSubmitButton = object : AjaxSubmitButton("save", StringResourceModel("gitki.save")) {
            override fun onSubmit(target: AjaxRequestTarget) {
                try {
                    wikiService
                        .save(
                            this@MarkdownEditPage.modelObject,
                            getGitkiSession().user!!,
                            commitMessageModel.getObject(),
                            contentModel.getObject()
                        )
                    this.session.info(this.getString("gitki.saved"))
                    target.add(lockLabel)
                } catch (e: LockedException) {
                    throw WicketRuntimeException(e)
                } catch (e: LockMissingException) {
                    throw WicketRuntimeException(e)
                } catch (e: GitAPIException) {
                    throw WicketRuntimeException(e)
                } catch (e: IOException) {
                    throw WicketRuntimeException(e)
                }
            }
        }
        saveButton.buttonStyle = ButtonStyle.PRIMARY
        editForm.add(saveButton)
        val cancelButton: Button<Void> = object : Button<Void>("cancel") {
            override fun onClick() {
                try {
                    wikiService.unlock(this@MarkdownEditPage.modelObject, getGitkiSession().user!!)
                    this.setResponsePage(
                        SimpleViewPage::class.java,
                        PageParameterUtils.from(this@MarkdownEditPage.modelObject)
                    )
                } catch (e: LockedException) {
                    throw WicketRuntimeException(e)
                }
            }
        }
        cancelButton.body = StringResourceModel("gitki.cancel")
        editForm.add(cancelButton)
    }

    /**
     * Creates default content for a new file.
     *
     * @return The content.
     */
    protected fun createDefaultContent(): String {
        val name = this.modelObject.nameWithoutExtension
        var content = name
        content += "\n"
        content += java.lang.String.join("", Collections.nCopies(name.length, "="))
        content += "\n\n"
        return content
    } //    @Override
    //    public void renderHead(IHeaderResponse response)
    //    {
    //        super.renderHead(response);
    //        response.render(new WoofmarkHeaderItem());
    //        response.render(new OnDomReadyHeaderItem(String.format(
    //                "woofmark(document.getElementById('%s'), {parseMarkdown: megamark, parseHTML: domador});",
    //                "markdown-content"
    //        )));
    //    }
}