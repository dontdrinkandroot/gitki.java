package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.wicket.page.HistoryPage;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import org.apache.wicket.Page;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class HistoryPageItem extends BookmarkablePageLinkItem<Void>
{
    public <C extends Page> HistoryPageItem(String id)
    {
        super(id, new StringResourceModel("gitki.history"), HistoryPage.class);
    }
}
