package net.dontdrinkandroot.gitki.wicket.page.file;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class AbstractFilePage extends DecoratorPage<FilePath>
{
    public AbstractFilePage(PageParameters parameters)
    {
        super(parameters);
        FilePath path = PageParameterUtils.toFilePath(parameters);
        this.setModel(Model.of(path));
    }

    public AbstractFilePage(IModel<FilePath> model)
    {
        super(model);
        PageParameterUtils.from(model.getObject(), this.getPageParameters());
    }

    @Override
    protected IModel<String> createTitleModel()
    {
        return new AbstractPathNameModel(this.getModel());
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        this.add(new Label("heading", this.getTitleModel()));
    }
}
