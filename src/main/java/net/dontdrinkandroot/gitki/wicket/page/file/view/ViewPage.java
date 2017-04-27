package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.FileActionsDropDownButton;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class ViewPage extends FilePage
{
    public ViewPage(PageParameters parameters)
    {
        super(parameters);
    }

    public ViewPage(IModel<FilePath> model)
    {
        super(model);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
    }

    @Override
    protected Component createActions(String id)
    {
        return new FileActionsDropDownButton(id, this.getModel());
    }
}
