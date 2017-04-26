package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.service.GitService;
import net.dontdrinkandroot.gitki.wicket.component.DirectoryActionsDropDownButton;
import net.dontdrinkandroot.gitki.wicket.component.DirectoryListPanel;
import net.dontdrinkandroot.gitki.wicket.component.FileListPanel;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.model.DirectoryListingFilesModel;
import net.dontdrinkandroot.gitki.wicket.model.DirectoryListingSubDirectoriesModel;
import net.dontdrinkandroot.gitki.wicket.model.DirectoryPathDirectoryListingModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryPage extends DecoratorPage<DirectoryPath>
{
    @Inject
    private GitService gitService;

    public DirectoryPage(PageParameters parameters)
    {
        super(parameters);

        DirectoryPath path = new DirectoryPath(parameters.get("path").toString());
        int indexedCount = parameters.getIndexedCount();
        for (int i = 0; i < indexedCount; i++) {
            path = path.appendDirectoryName(parameters.get(i).toString());
        }
        this.setModel(Model.of(path));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        this.add(new Label("heading", this.getTitleModel()));
        IModel<DirectoryListing> listingModel = new DirectoryPathDirectoryListingModel(this.getModel());

        this.add(new DirectoryActionsDropDownButton("actions", this.getModel()));
        this.add(new FileListPanel("files", new DirectoryListingFilesModel(listingModel)));
        this.add(new DirectoryListPanel("directories", new DirectoryListingSubDirectoriesModel(listingModel)));
    }

    @Override
    protected IModel<String> createTitleModel()
    {
        return new AbstractPathNameModel(this.getModel());
    }
}
