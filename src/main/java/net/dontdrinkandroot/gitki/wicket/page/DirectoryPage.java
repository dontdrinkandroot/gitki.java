package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import net.dontdrinkandroot.gitki.service.GitService;
import net.dontdrinkandroot.gitki.wicket.component.DirectoryListPanel;
import net.dontdrinkandroot.gitki.wicket.component.FileListPanel;
import net.dontdrinkandroot.gitki.wicket.model.DirectoryListingFilesModel;
import net.dontdrinkandroot.gitki.wicket.model.DirectoryListingSubDirectoriesModel;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryPage extends DecoratorPage<DirectoryListing>
{
    @Inject
    private GitService gitService;

    public DirectoryPage(PageParameters parameters)
    {
        super(parameters);

        Path path = Paths.get(parameters.get("path").toString(""));
        int indexedCount = parameters.getIndexedCount();
        for (int i = 0; i < indexedCount; i++) {
            path = path.resolve(parameters.get(i).toString(""));
        }

        try {
            this.setModel(Model.of(this.gitService.listDirectory(path)));
        } catch (FileNotFoundException e) {
            throw new AbortWithHttpErrorCodeException(404);
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        this.add(new FileListPanel("files", new DirectoryListingFilesModel(this.getModel())));
        this.add(new DirectoryListPanel("directories", new DirectoryListingSubDirectoriesModel(this.getModel())));
    }
}
