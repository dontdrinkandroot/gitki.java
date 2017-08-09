package net.dontdrinkandroot.gitki.wicket.model.lockinfo;

import net.dontdrinkandroot.gitki.model.LockInfo;
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;

import java.time.Instant;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class LockInfoExpiryModel extends AbstractChainedReadonlyModel<LockInfo, Instant>
{
    public LockInfoExpiryModel(IModel<? extends LockInfo> parent)
    {
        super(parent);
    }

    @Override
    public Instant getObject()
    {
        return this.getParentObject().getExpiry();
    }
}
