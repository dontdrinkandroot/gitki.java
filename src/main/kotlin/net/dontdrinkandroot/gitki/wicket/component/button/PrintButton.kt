package net.dontdrinkandroot.gitki.wicket.component.button

import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome4IconClass
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import org.apache.wicket.markup.html.WebMarkupContainer

class PrintButton(id: String) : WebMarkupContainer(id) {

    init {
        this.add(ButtonBehavior(buttonStyle = null))
        this.add(IconBehavior(FontAwesome5IconClass.PRINT.createIcon(fixedWidth = true)))
        this.add(OnClickScriptBehavior("window.print()"))
    }
}