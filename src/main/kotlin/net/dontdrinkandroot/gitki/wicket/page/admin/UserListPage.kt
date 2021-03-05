package net.dontdrinkandroot.gitki.wicket.page.admin

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.wicket.column.UserActionColumn
import net.dontdrinkandroot.gitki.wicket.component.button.UserAddButton
import net.dontdrinkandroot.gitki.wicket.dataprovider.UserDataProvider
import net.dontdrinkandroot.gitki.wicket.page.user.UserPage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.bootstrap.behavior.TableBehavior
import net.dontdrinkandroot.wicket.bootstrap.css.TableStyle
import net.dontdrinkandroot.wicket.kmodel.kModel
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import java.util.*

@Instantiate(Role.ADMIN)
class UserListPage(parameters: PageParameters = PageParameters()) : UserPage<List<User>>(parameters) {

    override fun createTitleModel() = kModel("Users")

    override fun onInitialize() {
        super.onInitialize()

        this.add(UserAddButton("addButton"))

        val dataProvider: SortableDataProvider<User, String> = UserDataProvider()
        val columns: MutableList<IColumn<User, String>> = ArrayList()
        columns.add(PropertyColumn(Model.of("Id"), "id", "id"))
        columns.add(PropertyColumn(StringResourceModel("gitki.user.email"), "email", "email"))
        columns.add(PropertyColumn(StringResourceModel("gitki.user.firstName"), "firstName", "firstName"))
        columns.add(PropertyColumn(StringResourceModel("gitki.user.lastName"), "lastName", "lastName"))
        columns.add(PropertyColumn(StringResourceModel("gitki.user.role"), "role", "role"))
        columns.add(UserActionColumn())
        val table = DataTable("table", columns, dataProvider, 20)
        table.addTopToolbar(HeadersToolbar(table, dataProvider))
        table.add(TableBehavior(null, TableStyle.STRIPED, TableStyle.SMALL))
        this.add(table)
    }
}