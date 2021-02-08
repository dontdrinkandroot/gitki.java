package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.admin.ErrorTestPage
import net.dontdrinkandroot.gitki.wicket.page.admin.LockListPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.Model
import org.apache.wicket.model.StringResourceModel

@Render(Role.ADMIN)
class AdminDropdownItem(id: String) : RepeatingDropdownItem<Void>(id, StringResourceModel("gitki.administration")) {

    override fun populateItems(itemView: RepeatingView) {
        itemView.add(UserListPageItem(itemView.newChildId()))
        itemView.add(ConfigurationPageItem(itemView.newChildId()))
        itemView.add(
            BookmarkablePageLinkItem<Void, LockListPage>(
                itemView.newChildId(),
                StringResourceModel("gitki.locks"),
                LockListPage::class.java
            )
        )
        itemView.add(
            BookmarkablePageLinkItem<Void, ErrorTestPage>(
                itemView.newChildId(),
                Model.of("Errors"),
                ErrorTestPage::class.java
            )
        )
    }
}