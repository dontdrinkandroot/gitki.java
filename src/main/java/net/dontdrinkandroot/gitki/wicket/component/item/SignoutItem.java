package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.wicket.GitkiWebApplication;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class SignoutItem extends AjaxLinkItem<Void>
{
    public SignoutItem(String id)
    {
        super(id, new StringResourceModel("gitki.signout"));
    }

    @Override
    protected void onClick(AjaxRequestTarget target)
    {
        GitkiWebSession.get().invalidate();
        this.setResponsePage(GitkiWebApplication.get().getHomePage());
    }
}
