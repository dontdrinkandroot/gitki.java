package net.dontdrinkandroot.gitki.wicket.component.button;

import net.dontdrinkandroot.wicket.behavior.OnClickScriptBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class PrintButton extends WebMarkupContainer
{
    public PrintButton(String id)
    {
        super(id);
        this.add(new ButtonBehavior());
        this.add(new IconBehavior(FontAwesomeIconClass.PRINT.createIcon().setFixedWidth(true)));
        this.add(new OnClickScriptBehavior("window.print()"));
    }
}
