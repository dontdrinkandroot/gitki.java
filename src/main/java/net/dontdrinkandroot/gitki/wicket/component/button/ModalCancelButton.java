package net.dontdrinkandroot.gitki.wicket.component.button;

import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.modal.Modal;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class ModalCancelButton extends Label
{
    public ModalCancelButton(String id, Modal modal)
    {
        super(id, new StringResourceModel("gitki.cancel"));
        this.add(new ButtonBehavior());
        this.add(new OnClickScriptBehavior(modal.getHideScript()));
    }
}
