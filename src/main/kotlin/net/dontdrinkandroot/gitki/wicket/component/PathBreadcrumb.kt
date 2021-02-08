package net.dontdrinkandroot.gitki.wicket.component

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.GitkiPath
import net.dontdrinkandroot.gitki.wicket.component.item.RootPathLinkItem
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.BootstrapCssClass
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

class PathBreadcrumb<T : GitkiPath>(id: String, model: IModel<T>) : GenericPanel<T>(id, model) {

    override fun onInitialize() {
        super.onInitialize()
        this.add(CssClassAppender(BootstrapCssClass.BREADCRUMB))
        val segmentView = RepeatingView("segment")
        this.add(segmentView)
        val paths: List<GitkiPath> = this.modelObject!!.segments
        for (path in paths) {
            var linkItem: BookmarkablePageLinkItem<*>
            if (path is DirectoryPath) {
                if (path.root) {
                    linkItem = RootPathLinkItem(segmentView.newChildId())
                } else {
                    linkItem = BookmarkablePageLinkItem<Any, DirectoryPage>(
                        segmentView.newChildId(),
                        Model.of(path.name),
                        DirectoryPage::class.java,
                        PageParameterUtils.from(path)
                    )
                }
            } else {
                linkItem = BookmarkablePageLinkItem<Any, ViewPage>(
                    segmentView.newChildId(),
                    Model.of(path.name),
                    ViewPage::class.java,
                    PageParameterUtils.from(path as FilePath)
                )
            }
            if (path == this.modelObject) {
                linkItem.add(CssClassAppender(BootstrapCssClass.ACTIVE))
            }
            segmentView.add(linkItem)
        }
    }
}