package net.dontdrinkandroot.gitki.wicket.page.file;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.page.BrowsePage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FilePage extends BrowsePage<FilePath>
{
    public FilePage(PageParameters parameters)
    {
        super(parameters);
        FilePath path = PageParameterUtils.toFilePath(parameters);
        this.setModel(Model.of(path));
    }

    public FilePage(IModel<FilePath> model)
    {
        super(model);
        PageParameterUtils.from(model.getObject(), this.getPageParameters());
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
    }
}
