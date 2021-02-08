package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.GitkiPath;
import net.dontdrinkandroot.wicket.model.AbstractChainedModel;
import org.apache.wicket.model.IModel;

public class AbstractPathNameModel extends AbstractChainedModel<GitkiPath, String>
{
    public AbstractPathNameModel(IModel<? extends GitkiPath> parent) {
        super(parent);
    }

    @Override
    public String getObject() {
        GitkiPath path = this.getParentObject();
        if (path.getRoot()) {
            return "/";
        }

        return path.getName();
    }
}
