package net.dontdrinkandroot.gitki.wicket.page.file.edit;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.page.file.AbstractFilePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class AbstractEditPage extends AbstractFilePage
{
    public AbstractEditPage(PageParameters parameters)
    {
        super(parameters);
    }

    public AbstractEditPage(IModel<FilePath> model)
    {
        super(model);
    }
}
