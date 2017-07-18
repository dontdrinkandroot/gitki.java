package net.dontdrinkandroot.gitki.wicket.page.user;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.wicket.form.UserEditForm;
import net.dontdrinkandroot.wicket.bootstrap.css.grid.ColumnSizeStack;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@AuthorizeInstantiation(Role.Constants.WATCHER)
public class UserEditPage extends UserPage<User>
{
    public UserEditPage(PageParameters parameters)
    {
        super(parameters);

        User user = new User();
        user.setRole(Role.WATCHER);
        this.setModel(Model.of(user));
    }

    public UserEditPage(IModel<User> model)
    {
        super(model);
    }

    @Override
    protected IModel<String> createTitleModel()
    {
        return Model.of("Edit User");
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        UserEditForm form = new UserEditForm("form", this.getModel());
        form.setHorizontal(ColumnSizeStack.FORM_DEFAULT);
        this.add(form);
    }
}
