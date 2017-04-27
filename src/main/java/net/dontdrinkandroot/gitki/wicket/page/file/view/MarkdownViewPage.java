package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.service.GitService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

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

        Parser parser = Parser.builder().build();
        try {
            Node document = parser.parse(this.gitService.getContentAsString(this.getModelObject().toPath()));
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            this.add(new Label("content", renderer.render(document)).setEscapeModelStrings(false));
        } catch (FileNotFoundException e) {
            throw new AbortWithHttpErrorCodeException(404);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
