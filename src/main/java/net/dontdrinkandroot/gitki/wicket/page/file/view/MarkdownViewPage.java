package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.service.markdown.MarkdownService;
import net.dontdrinkandroot.gitki.wicket.component.button.EditButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class MarkdownViewPage extends ViewPage
{
    @Inject
    private GitService gitService;

    @Inject
    private MarkdownService markdownService;

    public MarkdownViewPage(PageParameters parameters)
    {
        super(parameters);
    }

    public MarkdownViewPage(IModel<FilePath> model)
    {
        super(model);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        try {
            String renderedMarkdown =
                    this.markdownService.parseToHtml(this.gitService.getContentAsString(this.getModelObject()));
            this.add(new Label("content", renderedMarkdown).setEscapeModelStrings(false));
        } catch (FileNotFoundException e) {
            throw new AbortWithHttpErrorCodeException(404);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void populatePrimaryButtons(RepeatingView view)
    {
        view.add(new EditButton(view.newChildId(), this.getModel()));
        super.populatePrimaryButtons(view);
    }
}
