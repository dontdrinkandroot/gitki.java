package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.LockInfo;
import net.dontdrinkandroot.gitki.service.wiki.WikiService;
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class FilePathLockInfoModel extends AbstractChainedInjectedLoadableDetachableModel<FilePath, LockInfo>
{
    @SpringBean
    private WikiService wikiService;

    public FilePathLockInfoModel(IModel<? extends FilePath> parentModel) {
        super(parentModel);
    }

    @Override
    protected LockInfo load() {
        return this.wikiService.getLockInfo(this.getParentObject());
    }
}
