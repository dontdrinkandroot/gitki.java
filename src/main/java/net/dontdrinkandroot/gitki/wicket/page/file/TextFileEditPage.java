package net.dontdrinkandroot.gitki.wicket.page.file;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.service.GitService;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupTextArea;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class TextFileEditPage extends DecoratorPage<FilePath>
{
    @Inject
    private GitService gitService;

    private IModel<String> contentModel;

    private IModel<String> commitMessageModel;

    public TextFileEditPage(IModel<FilePath> model)
    {
        super(model);
        try {
            this.contentModel = Model.of(this.gitService.getContentAsString(model.getObject().toPath()));
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
        this.commitMessageModel = Model.of("Editing " + model.getObject().getName());
    }

    @Override
    protected IModel<String> createTitleModel()
    {
        return new AbstractPathNameModel(this.getModel());
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        this.contentModel = new Model<>();

        Form<String> editForm = new Form<>("editForm", this.contentModel);
        this.add(editForm);

        FormGroupTextArea<String> formGroupContent =
                new FormGroupTextArea<>("content", Model.of("Content"), this.contentModel);
        formGroupContent.setRows(20);
        editForm.add(formGroupContent);

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
                    TextFileEditPage.this.gitService.save(
                            TextFileEditPage.this.getModelObject().toPath(),
                            TextFileEditPage.this.contentModel.getObject(),
                            TextFileEditPage.this.commitMessageModel.getObject()
                    );
                } catch (IOException | GitAPIException e) {
                    throw new WicketRuntimeException(e);
                }
            }

            @Override
            protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form)
            {
                super.onAfterSubmit(target, form);
                this.setResponsePage(new FilePage(TextFileEditPage.this.getModel()));
            }
        };
        editForm.add(submitButton);
    }
}
