package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.GitkiPath;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;

public class AbstractPathBasicFileAttributesModel extends AbstractChainedInjectedLoadableDetachableModel<GitkiPath, BasicFileAttributes>
{
    @SpringBean
    private GitService gitService;

    public AbstractPathBasicFileAttributesModel(IModel<? extends GitkiPath> parentModel) {
        super(parentModel);
    }

    @Override
    protected BasicFileAttributes load() {
        try {
            return this.gitService.getBasicFileAttributes(this.getParentObject());
        } catch (IOException e) {
            throw new WicketRuntimeException(e);
        }
    }
}
