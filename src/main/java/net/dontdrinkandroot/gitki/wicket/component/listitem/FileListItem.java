package net.dontdrinkandroot.gitki.wicket.component.listitem;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileListItem extends GenericPanel<FilePath>
{
    public FileListItem(String id, IModel<FilePath> model)
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
                this.setResponsePage(new FilePage(FileListItem.this.getModel()));
            }
        };
        link.setBody(new AbstractPathNameModel(this.getModel()));
        this.add(link);
    }
}
