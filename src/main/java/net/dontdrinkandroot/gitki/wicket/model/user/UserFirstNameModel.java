package net.dontdrinkandroot.gitki.wicket.model.user;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.wicket.model.AbstractChainedModel;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserFirstNameModel extends AbstractChainedModel<User, String>
{
    public UserFirstNameModel(IModel<? extends User> parent)
    {
        super(parent);
    }

    @Override
    public String getObject()
    {
        return this.getParentObject().getFirstName();
    }

    @Override
    public void setObject(String object)
    {
        this.getParentObject().setFirstName(object);
    }
}
