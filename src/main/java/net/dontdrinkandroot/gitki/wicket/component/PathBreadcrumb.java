package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.item.RootPathLinkItem;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import net.dontdrinkandroot.wicket.bootstrap.css.BootstrapCssClass;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class PathBreadcrumb<T extends AbstractPath> extends GenericPanel<T>
{
    public PathBreadcrumb(String id, IModel<T> model)
    {
        super(id, model);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        this.add(new CssClassAppender(BootstrapCssClass.BREADCRUMB));

        RepeatingView segmentView = new RepeatingView("segment");
        this.add(segmentView);

        List<AbstractPath> paths = this.getModelObject().collectPaths();
        for (AbstractPath path : paths) {
            BookmarkablePageLinkItem linkItem;
            if (path instanceof DirectoryPath) {
                if (path.isRoot()) {
                    linkItem = new RootPathLinkItem(segmentView.newChildId());
                } else {
                    linkItem = new BookmarkablePageLinkItem(
                            segmentView.newChildId(),
                            Model.of(path.getName()),
                            DirectoryPage.class,
                            PageParameterUtils.from((DirectoryPath) path)
                    );
                }
            } else {
                linkItem = new BookmarkablePageLinkItem(
                        segmentView.newChildId(),
                        Model.of(path.getName()),
                        ViewPage.class,
                        PageParameterUtils.from((FilePath) path)
                );
            }

            if (path.equals(this.getModelObject())) {
                linkItem.add(new CssClassAppender(BootstrapCssClass.ACTIVE));
            }

            segmentView.add(linkItem);
        }
    }
}
