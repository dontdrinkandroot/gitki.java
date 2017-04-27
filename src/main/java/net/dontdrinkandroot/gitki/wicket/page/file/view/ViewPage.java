package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.FileActionsDropDownButton;
import net.dontdrinkandroot.gitki.wicket.event.FileDeletedEvent;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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
    protected void populatePrimaryButtons(RepeatingView view)
    {
        super.populatePrimaryButtons(view);
        view.add(new FileActionsDropDownButton(view.newChildId(), this.getModel()));
    }

    @Override
    public void onEvent(IEvent<?> event)
    {
        super.onEvent(event);

        Object payload = event.getPayload();
        if (payload instanceof FileDeletedEvent) {
            FilePath path = ((FileDeletedEvent) payload).getFilePath();
            if (path.equals(this.getModelObject())) {
                this.setResponsePage(new DirectoryPage(Model.of(path.getParent())));
            }
        }
    }
}
