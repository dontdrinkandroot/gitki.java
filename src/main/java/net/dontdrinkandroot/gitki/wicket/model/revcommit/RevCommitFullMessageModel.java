package net.dontdrinkandroot.gitki.wicket.model.revcommit;

import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class RevCommitFullMessageModel extends AbstractChainedReadonlyModel<RevCommit, String>
{
    public RevCommitFullMessageModel(IModel<? extends RevCommit> parent)
    {
        super(parent);
    }

    @Override
    public String getObject()
    {
        return this.getParentObject().getFullMessage();
    }
}
