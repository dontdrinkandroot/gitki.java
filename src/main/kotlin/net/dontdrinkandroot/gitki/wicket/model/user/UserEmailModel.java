package net.dontdrinkandroot.gitki.wicket.model.user;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.wicket.model.AbstractChainedModel;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserEmailModel extends AbstractChainedModel<User, String>
{
    public UserEmailModel(IModel<? extends User> parent) {
        super(parent);
    }

    @Override
    public String getObject() {
        return this.getParentObject().getEmail();
    }

    @Override
    public void setObject(String object) {
        this.getParentObject().setEmail(object);
    }
}
