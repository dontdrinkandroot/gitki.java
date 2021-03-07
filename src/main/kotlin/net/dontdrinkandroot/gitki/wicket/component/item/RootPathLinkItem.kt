package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.behavior.icon
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome4IconClass
import org.apache.wicket.model.Model

class RootPathLinkItem(id: String) : BookmarkablePageLinkItem<Any>(
    id,
    model = null,
    label = Model.of(""),
    pageClass = DirectoryPage::class.java,
    pageParameters = PageParameterUtils.from(DirectoryPath()),
    icon(FontAwesome4IconClass.HOME.createIcon())
)