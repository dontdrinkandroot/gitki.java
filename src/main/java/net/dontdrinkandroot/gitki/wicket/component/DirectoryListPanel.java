package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.wicket.component.listitem.DirectoryListItem;
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
public class DirectoryListPanel extends PlainPanel<List<DirectoryPath>>
{
    public DirectoryListPanel(String id, IModel<List<DirectoryPath>> model)
    {
        super(id, model);
    }

    @Override
    protected Component createHeading(String id)
    {
        return new PanelHeading(id, Model.of("Directories"), Heading.Level.H2);
    }

    @Override
    protected Component createAfterBody(String id)
    {
        ListGroup<DirectoryPath> list = new ListGroup<DirectoryPath>(id, this.getModel())
        {
            @Override
            protected Component createListComponent(String id, IModel<DirectoryPath> model)
            {
                return new DirectoryListItem(id, Model.of(model.getObject()));
            }
        };
        return list;
    }
}
