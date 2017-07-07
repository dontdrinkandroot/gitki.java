package net.dontdrinkandroot.gitki.wicket.page.directory;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.wicket.component.DirectoryActionsDropdownButton;
import net.dontdrinkandroot.gitki.wicket.component.DirectoryEntriesPanel;
import net.dontdrinkandroot.gitki.wicket.event.FileDeletedEvent;
import net.dontdrinkandroot.gitki.wicket.model.DirectoryPathEntriesModel;
import net.dontdrinkandroot.gitki.wicket.page.BrowsePage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryPage extends BrowsePage<DirectoryPath>
{
    private DirectoryEntriesPanel entriesPanel;

    public DirectoryPage(IModel<DirectoryPath> model)
    {
        super(model);
        PageParameterUtils.from(model.getObject(), this.getPageParameters());
    }

    public DirectoryPage(PageParameters parameters)
    {
        super(parameters);
        DirectoryPath path = PageParameterUtils.toDirectoryPath(parameters);
        this.setModel(Model.of(path));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        this.entriesPanel = new DirectoryEntriesPanel("entries", new DirectoryPathEntriesModel(this.getModel()));
        this.entriesPanel.setOutputMarkupId(true);
        this.add(this.entriesPanel);
    }

    @Override
    protected void populatePrimaryButtons(RepeatingView view)
    {
        super.populatePrimaryButtons(view);

        DirectoryActionsDropdownButton directoryActionsButton =
                new DirectoryActionsDropdownButton(view.newChildId(), this.getModel());
        directoryActionsButton.getIconBehavior()
                .setAppendIcon(FontAwesomeIconClass.ELLIPSIS_V.createIcon().setFixedWidth(true));
        view.add(directoryActionsButton);
    }

    @Override
    public void onEvent(IEvent<?> event)
    {
        super.onEvent(event);

        Object payload = event.getPayload();
        if (payload instanceof FileDeletedEvent) {
            FileDeletedEvent fileDeletedEvent = (FileDeletedEvent) payload;
            DirectoryPath directoryPath =
                    this.getGitService().findExistingDirectoryPath(fileDeletedEvent.getFilePath());
            if (directoryPath.equals(this.getModelObject())) {
                ((FileDeletedEvent) payload).getTarget().add(this.entriesPanel);
                return;
            }

            this.setResponsePage(new DirectoryPage(Model.of(directoryPath)));
        }
    }
}
