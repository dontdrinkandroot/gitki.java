package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome4IconClass
import net.dontdrinkandroot.wicket.kmodel.model
import org.apache.wicket.model.Model

class RootPathLinkItem(id: String) : BookmarkablePageLinkItem<Any>(
    id,
    labelModel = Model.of(""),
    prependIconModel = model(FontAwesome4IconClass.HOME.createIcon()),
    pageClass = DirectoryPage::class.java,
    pageParameters = PageParameterUtils.from(DirectoryPath())
)