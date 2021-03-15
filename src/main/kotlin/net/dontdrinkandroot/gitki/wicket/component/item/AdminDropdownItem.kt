package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.admin.ErrorTestPage
import net.dontdrinkandroot.gitki.wicket.page.admin.LockListPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.wicket.bootstrap.component.item.ItemView
import net.dontdrinkandroot.wicket.bootstrap.component.item.RepeatingDropdownItem
import net.dontdrinkandroot.wicket.bootstrap.component.item.addPageLink
import net.dontdrinkandroot.wicket.model.localize
import org.apache.wicket.model.StringResourceModel

@Render(Role.ADMIN)
class AdminDropdownItem(id: String) :
    RepeatingDropdownItem<Void>(id, labelModel = StringResourceModel("gitki.administration")) {

    override fun populateItems(itemView: ItemView) {
        itemView.add(UserListPageItem(itemView.newChildId()))
        itemView.add(ConfigurationPageItem(itemView.newChildId()))
        itemView.addPageLink(localize("gitki.locks"), LockListPage::class.java)
        itemView.addPageLink("Errors", ErrorTestPage::class.java)
    }
}