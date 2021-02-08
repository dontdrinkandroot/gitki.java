package net.dontdrinkandroot.gitki.wicket.page.user;

import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class UserPage<T> extends DecoratorPage<T>
{
    public UserPage(PageParameters parameters) {
        super(parameters);
    }

    public UserPage(IModel<T> model) {
        super(model);
    }
}
