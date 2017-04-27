package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class SimpleViewPage extends ViewPage
{
    public SimpleViewPage(PageParameters parameters)
    {
        super(parameters);
    }

    public SimpleViewPage(IModel<FilePath> model)
    {
        super(model);
    }
}
