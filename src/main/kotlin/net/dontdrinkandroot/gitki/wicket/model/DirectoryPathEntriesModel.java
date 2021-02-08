package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.GitkiPath;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class DirectoryPathEntriesModel extends AbstractChainedInjectedLoadableDetachableModel<DirectoryPath, List<GitkiPath>>
{
    @SpringBean
    private GitService gitService;

    public DirectoryPathEntriesModel(IModel<? extends DirectoryPath> parentModel) {
        super(parentModel);
    }

    @Override
    protected List<GitkiPath> load() {
        try {
            return this.gitService.listDirectory(this.getParentObject());
        } catch (FileNotFoundException e) {
            throw new AbortWithHttpErrorCodeException(404);
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }
}
