package net.dontdrinkandroot.gitki.wicket.component.listitem;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.page.DirectoryPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryListItem extends GenericPanel<DirectoryPath>
{
    public DirectoryListItem(String id, IModel<DirectoryPath> model)
    {
        super(id, model);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();
        AjaxLink<Void> link = new AjaxLink<Void>("link")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                DirectoryListItem.this.setResponsePage(new DirectoryPage(DirectoryListItem.this.getModel()));
            }
        };
        link.setBody(new AbstractPathNameModel(this.getModel()));
        this.add(link);
    }
}
