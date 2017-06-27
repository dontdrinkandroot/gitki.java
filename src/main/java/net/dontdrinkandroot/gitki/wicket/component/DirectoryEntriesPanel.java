package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.listitem.DirectoryListItem;
import net.dontdrinkandroot.gitki.wicket.component.listitem.FileListItem;
import net.dontdrinkandroot.wicket.bootstrap.component.list.ListGroup;
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PlainPanel;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryEntriesPanel extends PlainPanel<List<AbstractPath>>
{
    public DirectoryEntriesPanel(String id, IModel<List<AbstractPath>> model)
    {
        super(id, model);
    }

    @Override
    protected Component createAfterBody(String id)
    {
        return new ListGroup<AbstractPath>(id, this.getModel())
        {
            @Override
            protected Component createListComponent(String id, IModel<AbstractPath> model)
            {
                AbstractPath path = model.getObject();
                if (path.isDirectoryPath()) {
                    IModel<DirectoryPath> pathModel = Model.of((DirectoryPath) path);
                    return new DirectoryListItem(id, pathModel);
                } else {
                    IModel<FilePath> pathModel = Model.of((FilePath) path);
                    return new FileListItem(id, pathModel);
                }
            }
        };
    }
}
