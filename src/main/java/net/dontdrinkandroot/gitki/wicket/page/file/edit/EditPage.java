package net.dontdrinkandroot.gitki.wicket.page.file.edit;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class EditPage extends FilePage
{
    public EditPage(PageParameters parameters)
    {
        super(parameters);
    }

    public EditPage(IModel<FilePath> model)
    {
        super(model);
    }
}
