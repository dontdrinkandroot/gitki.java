package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.service.GitService;
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FilePathStringContentModel extends AbstractChainedInjectedLoadableDetachableModel<FilePath, String>
{
    @Inject
    private GitService gitService;

    public FilePathStringContentModel(IModel<? extends FilePath> parentModel)
    {
        super(parentModel);
    }

    @Override
    protected String load()
    {
        try {
            return this.gitService.getContentAsString(this.getParentObject().toPath());
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }
}
