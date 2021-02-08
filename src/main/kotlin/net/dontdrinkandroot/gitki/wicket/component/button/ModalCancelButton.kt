package net.dontdrinkandroot.gitki.wicket.component.button

import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.modal.Modal
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.StringResourceModel

class ModalCancelButton(id: String, modal: Modal<*>) : Label(id, StringResourceModel("gitki.cancel")) {

    init {
        add(ButtonBehavior())
        add(OnClickScriptBehavior(modal.hideScript))
    }
}