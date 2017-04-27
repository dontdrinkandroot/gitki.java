package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.listitem.FileListItem;
import net.dontdrinkandroot.wicket.bootstrap.component.list.ListGroup;
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PanelHeading;
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PlainPanel;
import net.dontdrinkandroot.wicket.component.basic.Heading;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileListPanel extends PlainPanel<List<FilePath>>
{
    public FileListPanel(String id, IModel<List<FilePath>> model)
    {
        super(id, model);
    }

    @Override
    protected Component createHeading(String id)
    {
        return new PanelHeading(id, Model.of("Files"), Heading.Level.H2);
    }

    @Override
    protected Component createAfterBody(String id)
    {
        ListGroup<FilePath> list = new ListGroup<FilePath>(id, this.getModel())
        {
            @Override
            protected Component createListComponent(String id, IModel<FilePath> model)
            {
                return new FileListItem(id, Model.of(model.getObject()));
            }
        };
        return list;
    }
}
