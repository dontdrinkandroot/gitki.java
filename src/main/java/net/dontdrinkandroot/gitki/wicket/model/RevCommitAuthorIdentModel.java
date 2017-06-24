package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class RevCommitAuthorIdentModel extends AbstractChainedReadonlyModel<RevCommit, PersonIdent>
{
    public RevCommitAuthorIdentModel(IModel<? extends RevCommit> parent)
    {
        super(parent);
    }

    @Override
    public PersonIdent getObject()
    {
        return this.getParentObject().getAuthorIdent();
    }
}
