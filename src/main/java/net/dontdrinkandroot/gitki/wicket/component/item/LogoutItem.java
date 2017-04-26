package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class LogoutItem extends AjaxLinkItem
{
    public LogoutItem(String id)
    {
        super(id, Model.of("Logout"));
    }

    @Override
    protected void onClick(AjaxRequestTarget target)
    {
        GitkiWebSession.get().invalidate();
        this.setResponsePage(GitkiWebApplication.get().getHomePage());
    }
}
