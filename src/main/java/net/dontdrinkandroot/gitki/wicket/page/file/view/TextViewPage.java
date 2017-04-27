package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.model.FilePathStringContentModel;
import net.dontdrinkandroot.gitki.wicket.page.file.AbstractFilePage;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class TextViewPage extends AbstractFilePage
{
    public TextViewPage(PageParameters parameters)
    {
        super(parameters);
    }

    public TextViewPage(IModel<FilePath> model)
    {
        super(model);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        this.add(new MultiLineLabel("content", new FilePathStringContentModel(this.getModel())));
    }
}
