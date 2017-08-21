package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.service.markdown.MarkdownService;
import net.dontdrinkandroot.gitki.wicket.component.button.EditButton;
import net.dontdrinkandroot.gitki.wicket.component.button.PrintButton;
import net.dontdrinkandroot.gitki.wicket.headeritem.HighlightInitHeaderItem;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class MarkdownViewPage extends ViewPage
{
    @SpringBean
    private GitService gitService;

    @SpringBean
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
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    protected void populatePrimaryButtons(RepeatingView view)
    {
        view.add(new EditButton(view.newChildId(), this.getModel()));
        view.add(new PrintButton(view.newChildId()));
        super.populatePrimaryButtons(view);
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);
        response.render(new HighlightInitHeaderItem());
    }
}
