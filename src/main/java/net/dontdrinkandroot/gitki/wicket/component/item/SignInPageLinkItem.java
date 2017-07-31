package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.page.SignInPage;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import org.apache.wicket.Page;
import org.apache.wicket.model.ResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class SignInPageLinkItem extends BookmarkablePageLinkItem
{
    public <C extends Page> SignInPageLinkItem(String id)
    {
        super(id, new ResourceModel("gitki.signin"), SignInPage.class);
    }

    @Override
    protected void onConfigure()
    {
        super.onConfigure();
        this.setVersioned(false);
        if (null != GitkiWebSession.get().getUser()) {
            this.setVisible(false);
        }
    }
}
