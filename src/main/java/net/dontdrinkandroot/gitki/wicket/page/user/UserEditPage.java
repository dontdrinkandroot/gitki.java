package net.dontdrinkandroot.gitki.wicket.page.user;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.user.UserService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.form.UserEditForm;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.bootstrap.css.grid.ColumnSizeStack;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.WATCHER)
public class UserEditPage extends UserPage<User>
{
    @SpringBean
    private UserService userService;

    public UserEditPage(PageParameters parameters)
    {
        super(parameters);

        User user = this.userService.find(GitkiWebSession.get().getUser().getId());
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
