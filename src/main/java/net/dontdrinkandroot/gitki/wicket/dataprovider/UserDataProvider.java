package net.dontdrinkandroot.gitki.wicket.dataprovider;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.user.UserService;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.Iterator;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserDataProvider extends SortableDataProvider<User, String>
{
    @Inject
    private UserService userService;

    public UserDataProvider()
    {
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends User> iterator(long first, long count)
    {
        return this.userService.find(first, count).iterator();
    }

    @Override
    public long size()
    {
        return this.userService.findCount();
    }

    @Override
    public IModel<User> model(User user)
    {
        return Model.of(user);
    }

    @Override
    public void detach()
    {
    }
}
