package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.admin.ConfigurationPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import org.apache.wicket.model.StringResourceModel

@Render(Role.ADMIN)
class ConfigurationPageItem(id: String) :
    BookmarkablePageLinkItem<Void>(
        id,
        label = StringResourceModel("gitki.configuration"),
        pageClass = ConfigurationPage::class.java
    )