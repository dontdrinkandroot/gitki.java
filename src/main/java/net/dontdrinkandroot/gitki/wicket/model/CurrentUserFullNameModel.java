package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import org.apache.wicket.model.AbstractReadOnlyModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class CurrentUserFullNameModel extends AbstractReadOnlyModel<String>
{
    @Override
    public String getObject()
    {
        User user = GitkiWebSession.get().getUser();
        if (null == user) {
            return "n/a";
        }

        return user.getFullName();
    }
}
