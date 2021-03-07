package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.wicket.getCurrentUser
import net.dontdrinkandroot.gitki.wicket.page.SignInPage
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import org.apache.wicket.model.ResourceModel

class SignInPageLinkItem(id: String) :
    BookmarkablePageLinkItem<Any>(id, label = ResourceModel("gitki.signin"), pageClass = SignInPage::class.java) {

    override fun onConfigure() {
        super.onConfigure()
        this.isVersioned = false
        if (null != getCurrentUser()) {
            this.isVisible = false
        }
    }
}