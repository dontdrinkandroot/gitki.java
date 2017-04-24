package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import net.dontdrinkandroot.wicket.model.AbstractChainedModel;
import org.apache.wicket.model.IModel;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryListingSubDirectoriesModel extends AbstractChainedModel<DirectoryListing, List<Path>>
{
    public DirectoryListingSubDirectoriesModel(IModel<? extends DirectoryListing> parent)
    {
        super(parent);
    }

    @Override
    public List<Path> getObject()
    {
        return this.getParentObject().getSubDirectories();
    }
}
