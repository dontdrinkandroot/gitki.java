package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.model.CurrentUserFullNameModel
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.StringResourceModel

@Render(Role.WATCHER)
class UserDropdownItem(id: String) : RepeatingDropdownItem<Any>(id, CurrentUserFullNameModel()) {

    override fun populateItems(itemView: RepeatingView) {
        itemView.add(
            BookmarkablePageLinkItem<Void, UserEditPage>(
                itemView.newChildId(), StringResourceModel("gitki.profile"),
                UserEditPage::class.java
            )
        )
        itemView.add(SignoutItem(itemView.newChildId()))
    }
}