package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.model.CurrentUserFullNameModel
import net.dontdrinkandroot.gitki.wicket.page.user.UserEditPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.component.item.ItemView
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem
import org.apache.wicket.model.StringResourceModel

@Render(Role.WATCHER)
class UserDropdownItem(id: String) : RepeatingDropdownItem<Any>(id, labelModel = CurrentUserFullNameModel()) {

    override fun populateItems(itemView: ItemView) {
        itemView.add(
            BookmarkablePageLinkItem<Void>(
                itemView.newChildId(),
                labelModel = StringResourceModel("gitki.profile"),
                pageClass = UserEditPage::class
            )
        )
        itemView.add(SignoutItem(itemView.newChildId()))
    }
}