package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.service.GitService;
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryPathDirectoryListingModel extends AbstractChainedInjectedLoadableDetachableModel<DirectoryPath, DirectoryListing>
{
    @Inject
    private GitService gitService;

    public DirectoryPathDirectoryListingModel(IModel<? extends DirectoryPath> parentModel)
    {
        super(parentModel);
    }

    @Override
    protected DirectoryListing load()
    {
        try {
            return this.gitService.listDirectory(this.getParentObject().toPath());
        } catch (FileNotFoundException e) {
            throw new AbortWithHttpErrorCodeException(404);
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }
}
