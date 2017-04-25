package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.wicket.model.AbstractChainedModel;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class AbstractPathNameModel extends AbstractChainedModel<AbstractPath, String>
{
    public AbstractPathNameModel(IModel<? extends AbstractPath> parent)
    {
        super(parent);
    }

    @Override
    public String getObject()
    {
        return this.getParentObject().getName();
    }
}
