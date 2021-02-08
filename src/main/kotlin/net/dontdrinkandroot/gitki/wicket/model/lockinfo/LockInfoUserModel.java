package net.dontdrinkandroot.gitki.wicket.model.lockinfo;

import net.dontdrinkandroot.gitki.model.GitUser;
import net.dontdrinkandroot.gitki.model.LockInfo;
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class LockInfoUserModel extends AbstractChainedReadonlyModel<LockInfo, GitUser>
{
    public LockInfoUserModel(IModel<? extends LockInfo> parent) {
        super(parent);
    }

    @Override
    public GitUser getObject() {
        return this.getParentObject().getUser();
    }
}
