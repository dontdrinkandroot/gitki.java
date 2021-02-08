package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.button.EditButton;
import net.dontdrinkandroot.gitki.wicket.component.button.PrintButton;
import net.dontdrinkandroot.gitki.wicket.headeritem.HighlightInitHeaderItem;
import net.dontdrinkandroot.gitki.wicket.model.FilePathStringContentModel;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class TextViewPage extends ViewPage
{
    public TextViewPage(PageParameters parameters) {
        super(parameters);
    }

    public TextViewPage(IModel<FilePath> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.add(new Label("content", new FilePathStringContentModel(this.getModel())));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(new HighlightInitHeaderItem());
    }

    @Override
    protected void populatePrimaryButtons(RepeatingView view) {
        view.add(new EditButton(view.newChildId(), this.getModel()));
        view.add(new PrintButton(view.newChildId()));
        super.populatePrimaryButtons(view);
    }
}
