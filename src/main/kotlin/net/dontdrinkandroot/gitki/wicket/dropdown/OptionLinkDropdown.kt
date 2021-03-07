package net.dontdrinkandroot.gitki.wicket.dropdown

import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.behavior.DropdownToggleBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.dropdown.DropdownMenu
import net.dontdrinkandroot.wicket.bootstrap.component.item.ItemView
import net.dontdrinkandroot.wicket.bootstrap.css.BootstrapCssClass
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import net.dontdrinkandroot.wicket.markup.html.markupContainer
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.Model

class OptionLinkDropdown(
    id: String,
    populateItemsHandler: OptionLinkDropdown.(itemView: RepeatingView) -> Any?
) : Panel(id) {

    init {
        this.add(CssClassAppender(BootstrapCssClass.DROPDOWN))
        markupContainer(
            "link",
            DropdownToggleBehavior(),
            IconBehavior(FontAwesome5IconClass.ELLIPSIS_V.createIcon(fixedWidth = true))
        )
        this.add(object : DropdownMenu("dropdownMenu", Model(DropdownAlignment.END)) {
            override fun populateItems(itemView: ItemView) {
                populateItemsHandler(itemView)
            }
        })
    }
}