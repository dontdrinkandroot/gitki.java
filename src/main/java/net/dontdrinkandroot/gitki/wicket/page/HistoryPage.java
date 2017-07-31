package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.component.bspanel.RevCommitPanel;
import net.dontdrinkandroot.gitki.wicket.dataprovider.HistoryDataProvider;
import net.dontdrinkandroot.wicket.bootstrap.component.pagination.AjaxPaginationPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class HistoryPage extends DecoratorPage<Void>
{
    public HistoryPage(PageParameters parameters)
    {
        super(parameters);
        GitkiWebSession.get().assertAnonymousBrowsing(HistoryPage.class);
    }

    @Override
    protected IModel<String> createTitleModel()
    {
        return new StringResourceModel("gitki.history");
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        WebMarkupContainer commitContainer = new WebMarkupContainer("commitContainer");
        commitContainer.setOutputMarkupId(true);
        this.add(commitContainer);

        DataView<RevCommit> commitView = new DataView<RevCommit>("commit", new HistoryDataProvider())
        {
            @Override
            protected void populateItem(Item<RevCommit> item)
            {
                item.add(new RevCommitPanel("detail", item.getModel()));
            }
        };
        commitView.setItemsPerPage(20);
        commitContainer.add(commitView);

        AjaxPaginationPanel pagination = new AjaxPaginationPanel("pagination", commitView)
        {
            @Override
            protected void onPageChanged(AjaxRequestTarget target)
            {
                target.add(this);
                target.add(commitContainer);
            }
        };
        this.add(pagination);
    }
}
