package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.wicket.component.bspanel.RevCommitPanel
import net.dontdrinkandroot.gitki.wicket.dataprovider.HistoryDataProvider
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.wicket.bootstrap.component.pagination.AjaxPaginationPanel
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.markup.repeater.data.DataView
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.eclipse.jgit.revwalk.RevCommit

class HistoryPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    init {
        getGitkiSession().assertAnonymousBrowsing(HistoryPage::class.java)
    }

    override fun createTitleModel() = StringResourceModel("gitki.history")

    override fun onInitialize() {
        super.onInitialize()
        val commitContainer = WebMarkupContainer("commitContainer")
        commitContainer.outputMarkupId = true
        this.add(commitContainer)
        val commitView: DataView<RevCommit> = object : DataView<RevCommit>("commit", HistoryDataProvider()) {
            override fun populateItem(item: Item<RevCommit>) {
                item.add(RevCommitPanel("detail", item.model))
            }
        }
        commitView.itemsPerPage = 20
        commitContainer.add(commitView)
        val pagination: AjaxPaginationPanel = object : AjaxPaginationPanel("pagination", commitView) {
            override fun onPageChanged(target: AjaxRequestTarget) {
                target.add(this)
                target.add(commitContainer)
            }
        }
        this.add(pagination)
    }
}