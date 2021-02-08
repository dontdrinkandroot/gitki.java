package net.dontdrinkandroot.gitki.wicket.model.personident;

import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;
import org.eclipse.jgit.lib.PersonIdent;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class PersonIdentNameModel extends AbstractChainedReadonlyModel<PersonIdent, String>
{
    public PersonIdentNameModel(IModel<? extends PersonIdent> parent) {
        super(parent);
    }

    @Override
    public String getObject() {
        return this.getParentObject().getName();
    }
}
