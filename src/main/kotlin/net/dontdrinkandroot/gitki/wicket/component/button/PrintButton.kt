package net.dontdrinkandroot.gitki.wicket.component.button

import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome4IconClass
import org.apache.wicket.markup.html.WebMarkupContainer

class PrintButton(id: String) : WebMarkupContainer(id) {

    init {
        this.add(ButtonBehavior())
        this.add(IconBehavior(FontAwesome4IconClass.PRINT.createIcon().apply { fixedWidth = true }))
        this.add(OnClickScriptBehavior("window.print()"))
    }
}