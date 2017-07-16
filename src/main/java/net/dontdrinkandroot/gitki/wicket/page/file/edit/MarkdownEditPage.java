package net.dontdrinkandroot.gitki.wicket.page.file.edit;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.service.markdown.MarkdownService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupTextArea;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.attributes.ThrottlingSettings;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class MarkdownEditPage extends EditPage
{
    @SpringBean
    private GitService gitService;

    @SpringBean
    private MarkdownService markdownService;

    private IModel<String> contentModel;

    private IModel<String> commitMessageModel;

    private FormGroupTextArea<String> formGroupContent;

    public MarkdownEditPage(PageParameters parameters)
    {
        super(parameters);
    }

    public MarkdownEditPage(IModel<FilePath> model)
    {
        super(model);
        this.getPageParameters().set("action", "edit");
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        try {
            this.contentModel = Model.of(this.gitService.getContentAsString(this.getModelObject()));
        } catch (FileNotFoundException e) {
            this.contentModel = Model.of("");
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
        this.commitMessageModel = Model.of("Editing " + this.getModelObject().toAbsoluteString());

        Label preview = new Label("preview", new AbstractReadOnlyModel<String>()
        {
            @Override
            public String getObject()
            {
                return MarkdownEditPage.this.markdownService.parseToHtml(MarkdownEditPage.this.contentModel.getObject());
            }
        });
        preview.setEscapeModelStrings(false);
        preview.setOutputMarkupId(true);
        this.add(preview);

        Form<String> editForm = new Form<>("editForm", this.contentModel);
        this.add(editForm);

        this.formGroupContent =
                new FormGroupTextArea<>("content", Model.of("Content"), this.contentModel);
        this.formGroupContent.setRows(20);
        this.formGroupContent.getFormComponent().add(new AjaxFormComponentUpdatingBehavior("input")
        {
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes)
            {
                super.updateAjaxAttributes(attributes);
                attributes.setThrottlingSettings(new ThrottlingSettings(
                        "markdownpreview",
                        Duration.milliseconds(250),
                        true
                ));
            }

            @Override
            protected void onUpdate(AjaxRequestTarget target)
            {
                target.add(preview);
            }
        });
        editForm.add(this.formGroupContent);

        FormGroupInputText formGroupCommitMessage =
                new FormGroupInputText("commitMessage", Model.of("Commit message"), this.commitMessageModel);
        formGroupCommitMessage.setRequired(true);
        editForm.add(formGroupCommitMessage);

        AjaxSubmitButton submitButton = new AjaxSubmitButton("submit", Model.of("Save"))
        {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form)
            {
                super.onSubmit(target, form);
                try {
                    MarkdownEditPage.this.gitService.addAndCommit(
                            MarkdownEditPage.this.getModelObject(),
                            MarkdownEditPage.this.contentModel.getObject(),
                            GitkiWebSession.get().getUser(),
                            MarkdownEditPage.this.commitMessageModel.getObject()
                    );
                } catch (IOException | GitAPIException e) {
                    throw new WicketRuntimeException(e);
                }
            }

            @Override
            protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
            {
                super.onAfterSubmit(target, form);
                this.setResponsePage(
                        SimpleViewPage.class,
                        PageParameterUtils.from(MarkdownEditPage.this.getModelObject())
                );
            }
        };
        editForm.add(submitButton);
    }

    //    @Override
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
