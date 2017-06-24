package net.dontdrinkandroot.gitki.wicket.page;

import net.dontdrinkandroot.gitki.wicket.dataprovider.HistoryDataProvider;
import net.dontdrinkandroot.gitki.wicket.model.PersonIdentWhenModel;
import net.dontdrinkandroot.gitki.wicket.model.RevCommitAuthorIdentModel;
import net.dontdrinkandroot.wicket.bootstrap.component.pagination.AjaxPaginationPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class HistoryPage extends DecoratorPage<Void>
{
    @Override
    protected IModel<String> createTitleModel()
    {
        return Model.of("History");
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
                item.add(new Label("message", item.getModelObject().getFullMessage()));
                item.add(new Label("authorName", item.getModelObject().getAuthorIdent().getName()));
                item.add(new Label("authorEmail", item.getModelObject().getAuthorIdent().getEmailAddress()));
                item.add(new Label(
                        "authorDate",
                        new PersonIdentWhenModel(new RevCommitAuthorIdentModel(item.getModel()))
                ));
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
