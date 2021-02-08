package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.component.FileActionsDropdownButton;
import net.dontdrinkandroot.gitki.wicket.component.button.DownloadButton;
import net.dontdrinkandroot.gitki.wicket.event.FileDeletedEvent;
import net.dontdrinkandroot.gitki.wicket.event.FileMovedEvent;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(value = Role.WATCHER, allowAnonymousIfConfigured = true)
public class ViewPage extends FilePage
{
    public ViewPage(PageParameters parameters) {
        super(parameters);
        if (!this.getGitService().exists(this.getModelObject())) {
            throw new AbortWithHttpErrorCodeException(404);
        }
    }

    public ViewPage(IModel<FilePath> model) {
        super(model);
        if (!this.getGitService().exists(model.getObject())) {
            throw new AbortWithHttpErrorCodeException(404);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    protected void populatePrimaryButtons(RepeatingView view) {
        super.populatePrimaryButtons(view);
        view.add(new DownloadButton(view.newChildId(), this.getModel()));
        view.add(new FileActionsDropdownButton(view.newChildId(), this.getModel()));
    }

    @Override
    public void onEvent(IEvent<?> event) {
        super.onEvent(event);

        Object payload = event.getPayload();
        if (payload instanceof FileDeletedEvent) {
            FileDeletedEvent fileDeletedEvent = (FileDeletedEvent) payload;
            DirectoryPath directoryPath =
                    this.getGitService().findExistingDirectoryPath(fileDeletedEvent.getFilePath());
            this.setResponsePage(new DirectoryPage(Model.of(directoryPath)));
        }

        if (payload instanceof FileMovedEvent) {
            FileMovedEvent fileMovedEvent = (FileMovedEvent) payload;
            this.setResponsePage(SimpleViewPage.class, PageParameterUtils.from(fileMovedEvent.getTargetPath()));
        }
    }
}
