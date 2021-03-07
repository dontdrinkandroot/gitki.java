package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.wicket.component.commit.RevCommitPanel
import net.dontdrinkandroot.gitki.wicket.dataprovider.HistoryDataProvider
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.wicket.behavior.appendCssClass
import net.dontdrinkandroot.wicket.behavior.outputMarkupId
import net.dontdrinkandroot.wicket.bootstrap.component.pagination.AjaxPaginationPanel
import net.dontdrinkandroot.wicket.bootstrap.css.BootstrapCssClass
import net.dontdrinkandroot.wicket.kmodel.wrap
import net.dontdrinkandroot.wicket.markup.repeater.data.repeatingDataView
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.request.mapper.parameter.PageParameters

class HistoryPage(parameters: PageParameters) : DecoratorPage<Void>(parameters) {

    init {
        getGitkiSession().assertAnonymousBrowsing(HistoryPage::class.java)
    }

    override fun createTitleModel() = StringResourceModel("gitki.history").wrap()

    override fun onInitialize() {
        super.onInitialize()

        val commitView = repeatingDataView(
            "commit",
            HistoryDataProvider(),
            25,
            outputMarkupId(),
            appendCssClass(BootstrapCssClass.LIST_GROUP)
        ) { id, model ->
            RevCommitPanel(id, model, appendCssClass(BootstrapCssClass.LIST_GROUP_ITEM))
        }
        add(commitView)
        add(AjaxPaginationPanel("pagination", commitView))
    }
}