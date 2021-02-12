package net.dontdrinkandroot.gitki.wicket.dropdown

import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.behavior.DropdownToggleBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.dropdown.DropdownMenu
import net.dontdrinkandroot.wicket.bootstrap.css.BootstrapCssClass
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import org.apache.wicket.Component
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.markup.repeater.RepeatingView

abstract class OptionLinkDropdown(id: String) : Panel(id) {

    override fun onInitialize() {
        super.onInitialize()
        this.add(CssClassAppender(BootstrapCssClass.DROPDOWN))
        val link: Component = WebMarkupContainer("link")
        link.add(IconBehavior(FontAwesome5IconClass.ELLIPSIS_V.createIcon().apply { fixedWidth = true }))
        link.add(DropdownToggleBehavior())
        this.add(link)
        val dropdownMenu: DropdownMenu = object : DropdownMenu("dropdownMenu") {
            override fun populateItems(itemView: RepeatingView) {
                this@OptionLinkDropdown.populateItems(itemView)
            }
        }
        dropdownMenu.setAlignment(DropdownAlignment.RIGHT)
        this.add(dropdownMenu)
    }

    protected abstract fun populateItems(itemView: RepeatingView)
}