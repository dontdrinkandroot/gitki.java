package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.Page;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class RootPathLinkItem extends BookmarkablePageLinkItem
{
    public <C extends Page> RootPathLinkItem(String id)
    {
        super(id, Model.of(""), DirectoryPage.class, PageParameterUtils.from(new DirectoryPath()));
        this.setPrependIcon(FontAwesomeIconClass.HOME.createIcon());
    }
}
