package net.dontdrinkandroot.gitki.wicket.component.bspanel.index;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.service.markdown.MarkdownService;
import net.dontdrinkandroot.gitki.wicket.headeritem.HighlightInitHeaderItem;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class MarkdownIndexFilePanel extends IndexFilePanel
{
    @SpringBean
    private GitService gitService;

    @SpringBean
    private MarkdownService markdownService;

    public MarkdownIndexFilePanel(String id, IModel<FilePath> model)
    {
        super(id, model);
    }

    @Override
    protected Component createBody(String id)
    {
        try {
            String renderedMarkdown =
                    this.markdownService.parseToHtml(this.gitService.getContentAsString(this.getModelObject()));
            Component body = new Label(id, renderedMarkdown).setEscapeModelStrings(false);
            body.add(new CssClassAppender("content"));
            body.add(new CssClassAppender("markdown"));

            return body;
        } catch (FileNotFoundException e) {
            throw new AbortWithHttpErrorCodeException(404);
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        super.renderHead(response);
        response.render(new HighlightInitHeaderItem());
    }
}
