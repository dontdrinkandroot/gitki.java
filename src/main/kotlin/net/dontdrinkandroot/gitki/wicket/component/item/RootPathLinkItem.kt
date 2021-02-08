package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass
import org.apache.wicket.model.Model

class RootPathLinkItem(id: String) : BookmarkablePageLinkItem<Any>(
    id,
    Model.of(""),
    DirectoryPage::class.java,
    PageParameterUtils.from(DirectoryPath())
) {

    init {
        setPrependIcon(FontAwesomeIconClass.HOME.createIcon())
    }
}