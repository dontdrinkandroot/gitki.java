package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.wicket.page.HistoryPage
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import org.apache.wicket.model.StringResourceModel

class HistoryPageItem(id: String) :
    BookmarkablePageLinkItem<Void>(
        id,
        labelModel = StringResourceModel("gitki.history"),
        pageClass = HistoryPage::class.java
    )