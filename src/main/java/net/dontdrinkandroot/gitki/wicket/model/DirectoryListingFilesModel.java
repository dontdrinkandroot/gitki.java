package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.wicket.model.AbstractChainedModel;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryListingFilesModel extends AbstractChainedModel<DirectoryListing, List<FilePath>>
{
    public DirectoryListingFilesModel(IModel<? extends DirectoryListing> parent)
    {
        super(parent);
    }

    @Override
    public List<FilePath> getObject()
    {
        return this.getParentObject().getFiles();
    }
}
