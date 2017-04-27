package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class AbstractPathAbsoluteStringModel extends AbstractChainedReadonlyModel<AbstractPath, String>
{
    public AbstractPathAbsoluteStringModel(IModel<? extends AbstractPath> parent)
    {
        super(parent);
    }

    @Override
    public String getObject()
    {
        return this.getParentObject().toAbsoluteString();
    }
}
