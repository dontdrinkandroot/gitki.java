package net.dontdrinkandroot.gitki.wicket.page.admin;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.wicket.column.UserActionColumn;
import net.dontdrinkandroot.gitki.wicket.component.button.UserAddButton;
import net.dontdrinkandroot.gitki.wicket.dataprovider.UserDataProvider;
import net.dontdrinkandroot.gitki.wicket.page.user.UserPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.css.BootstrapCssClass;
import net.dontdrinkandroot.wicket.bootstrap.css.TableStyle;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.ADMIN)
public class UserListPage extends UserPage<List<User>>
{
    public UserListPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected IModel<String> createTitleModel() {
        return Model.of("Users");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new UserAddButton("addButton"));

        SortableDataProvider<User, String> dataProvider = new UserDataProvider();

        List<IColumn<User, String>> columns = new ArrayList<>();
        columns.add(new PropertyColumn<>(Model.of("Id"), "id", "id"));
        columns.add(new PropertyColumn<>(Model.of("Email"), "email", "email"));
        columns.add(new PropertyColumn<>(Model.of("First Name"), "firstName", "firstName"));
        columns.add(new PropertyColumn<>(Model.of("Last Name"), "lastName", "lastName"));
        columns.add(new PropertyColumn<>(Model.of("Role"), "role", "role"));
        columns.add(new UserActionColumn());

        DataTable<User, String> table = new DataTable<>("table", columns, dataProvider, 20);
        table.addTopToolbar(new HeadersToolbar<>(table, dataProvider));
        table.add(new CssClassAppender(BootstrapCssClass.TABLE));
        table.add(new CssClassAppender(TableStyle.STRIPED));
        table.add(new CssClassAppender(TableStyle.SMALL));
        this.add(table);
    }
}
