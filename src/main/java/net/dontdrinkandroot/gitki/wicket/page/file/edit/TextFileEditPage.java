package net.dontdrinkandroot.gitki.wicket.page.file.edit;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.GitService;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxSubmitButton;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupInputText;
import net.dontdrinkandroot.wicket.bootstrap.component.form.formgroup.FormGroupTextArea;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.jgit.api.errors.GitAPIException;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class TextFileEditPage extends AbstractEditPage
{
    @Inject
    private GitService gitService;

    private IModel<String> contentModel;

    private IModel<String> commitMessageModel;

    public TextFileEditPage(PageParameters parameters)
    {
        super(parameters);
    }

    public TextFileEditPage(IModel<FilePath> model)
    {
        super(model);
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

        try {
            this.contentModel = Model.of(this.gitService.getContentAsString(this.getModelObject().toPath()));
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
        this.commitMessageModel = Model.of("Editing " + this.getModelObject().getName());

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
                this.setResponsePage(
                        SimpleViewPage.class,
                        PageParameterUtils.from(TextFileEditPage.this.getModelObject())
                );
            }
        };
        editForm.add(submitButton);
    }
}
