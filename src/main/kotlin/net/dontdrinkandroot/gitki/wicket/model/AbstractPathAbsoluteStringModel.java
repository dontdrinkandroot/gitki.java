package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.GitkiPath;
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;

public class AbstractPathAbsoluteStringModel extends AbstractChainedReadonlyModel<GitkiPath, String>
{
    public AbstractPathAbsoluteStringModel(IModel<? extends GitkiPath> parent) {
        super(parent);
    }

    @Override
    public String getObject() {
        return this.getParentObject().getAbsoluteString();
    }
}
