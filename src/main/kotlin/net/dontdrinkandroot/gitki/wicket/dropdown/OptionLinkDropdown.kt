package net.dontdrinkandroot.gitki.wicket.dropdown

import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.behavior.dropdownToggle
import net.dontdrinkandroot.wicket.bootstrap.behavior.icon
import net.dontdrinkandroot.wicket.bootstrap.component.dropdown.DropdownMenu
import net.dontdrinkandroot.wicket.bootstrap.component.item.ItemView
import net.dontdrinkandroot.wicket.bootstrap.css.BootstrapCssClass
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment
import net.dontdrinkandroot.wicket.markup.html.markupContainer
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.Model
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass as Fa5IconClass

class OptionLinkDropdown(
    id: String,
    populateItemsHandler: ItemView.() -> Any?
) : Panel(id) {

    init {
        this.add(CssClassAppender(BootstrapCssClass.DROPDOWN))
        markupContainer("link", dropdownToggle(), icon(Fa5IconClass.ELLIPSIS_V.createIcon(fixedWidth = true)))
        this.add(object : DropdownMenu("dropdownMenu", Model(DropdownAlignment.END)) {
            override fun populateItems(itemView: ItemView) {
                populateItemsHandler(itemView)
            }
        })
    }
}