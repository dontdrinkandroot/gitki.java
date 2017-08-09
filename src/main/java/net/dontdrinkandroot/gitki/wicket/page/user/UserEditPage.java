package net.dontdrinkandroot.gitki.wicket.page.user;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.user.UserService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.form.UserEditForm;
import net.dontdrinkandroot.gitki.wicket.page.admin.UserListPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.bootstrap.css.grid.ColumnSizeStack;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
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
        return new StringResourceModel("gitki.user_edit");
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        UserEditForm form = new UserEditForm("form", this.getModel())
        {
            @Override
            protected void onAfterSubmit(AjaxRequestTarget target)
            {
                super.onAfterSubmit(target);

                if (GitkiWebSession.get().hasRole(Role.ADMIN)) {
                    this.setResponsePage(UserListPage.class);
                }
            }
        };
        form.setHorizontal(ColumnSizeStack.FORM_DEFAULT);
        this.add(form);
    }
}
