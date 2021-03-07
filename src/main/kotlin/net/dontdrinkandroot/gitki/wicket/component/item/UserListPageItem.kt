package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.admin.UserListPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import org.apache.wicket.model.StringResourceModel

@Render(Role.ADMIN)
class UserListPageItem(id: String) :
    BookmarkablePageLinkItem<Any>(
        id,
        label = StringResourceModel("gitki.users"),
        pageClass = UserListPage::class.java
    )