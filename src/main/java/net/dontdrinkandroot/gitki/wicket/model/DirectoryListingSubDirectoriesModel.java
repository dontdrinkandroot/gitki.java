package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.wicket.model.AbstractChainedModel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryListingSubDirectoriesModel extends AbstractChainedModel<DirectoryListing, List<DirectoryPath>>
{
    public DirectoryListingSubDirectoriesModel(IModel<? extends DirectoryListing> parent)
    {
        super(parent);
    }

    @Override
    public List<DirectoryPath> getObject()
    {
        return this.getParentObject().getSubDirectories();
    }
}
