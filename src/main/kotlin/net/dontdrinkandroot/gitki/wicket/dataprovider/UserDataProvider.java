package net.dontdrinkandroot.gitki.wicket.dataprovider;

import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.user.UserService;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Iterator;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class UserDataProvider extends SortableDataProvider<User, String>
{
    @SpringBean
    private UserService userService;

    public UserDataProvider() {
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends User> iterator(long first, long count) {
        SortParam<String> sort = this.getSort();
        if (null == sort) {
            sort = new SortParam<>("id", true);
        }
        return this.userService.find(first, count, sort.getProperty(), sort.isAscending());
    }

    @Override
    public long size() {
        return this.userService.findCount();
    }

    @Override
    public IModel<User> model(User user) {
        return Model.of(user);
    }

    @Override
    public void detach() {
    }
}
