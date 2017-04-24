package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.wicket.bootstrap.component.list.ListGroup;
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PanelHeading;
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PlainPanel;
import net.dontdrinkandroot.wicket.component.basic.Heading;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.nio.file.Path;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileListPanel extends PlainPanel<List<Path>>
{
    public FileListPanel(String id, IModel<List<Path>> model)
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
        ListGroup<Path> list = new ListGroup<Path>(id, this.getModel())
        {
            @Override
            protected Component createListComponent(String id, IModel<Path> model)
            {
                return new Label(id, model);
            }
        };
        return list;
    }
}
