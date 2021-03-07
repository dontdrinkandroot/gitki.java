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
import net.dontdrinkandroot.wicket.kmodel.KModel
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.Model

class PathBreadcrumb<T : GitkiPath>(id: String, model: KModel<T>) : GenericPanel<T>(id, model) {

    override fun onInitialize() {
        super.onInitialize()
        this.add(CssClassAppender(BootstrapCssClass.BREADCRUMB))
        val segmentView = RepeatingView("segment")
        this.add(segmentView)
        val paths: List<GitkiPath> = this.modelObject!!.segments
        for (path in paths) {
            val linkItem = createLinkItem(segmentView.newChildId(), path)
            if (path == this.modelObject) linkItem.add(CssClassAppender(BootstrapCssClass.ACTIVE))
            segmentView.add(linkItem)
        }
    }

    private fun createLinkItem(id: String, path: GitkiPath): BookmarkablePageLinkItem<*> {
        when (path) {
            is DirectoryPath -> {
                return when {
                    path.root -> RootPathLinkItem(id).apply {
                        add(CssClassAppender(BootstrapCssClass.BREADCRUMB_ITEM))
                    }
                    else -> BookmarkablePageLinkItem<Void>(
                        id,
                        label = Model.of(path.name),
                        pageClass = DirectoryPage::class.java,
                        pageParameters = PageParameterUtils.from(path)
                    ).apply { add(CssClassAppender(BootstrapCssClass.BREADCRUMB_ITEM)) }
                }
            }
            else -> {
                return BookmarkablePageLinkItem<Void>(
                    id,
                    label = Model.of(path.name),
                    pageClass = ViewPage::class.java,
                    pageParameters = PageParameterUtils.from(path as FilePath)
                ).apply { add(CssClassAppender(BootstrapCssClass.BREADCRUMB_ITEM)) }
            }
        }
    }
}