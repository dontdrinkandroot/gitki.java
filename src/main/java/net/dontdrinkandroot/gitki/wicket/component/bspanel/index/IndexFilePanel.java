package net.dontdrinkandroot.gitki.wicket.component.bspanel.index;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PanelHeading;
import net.dontdrinkandroot.wicket.bootstrap.component.panel.PlainPanel;
import net.dontdrinkandroot.wicket.component.basic.Heading;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class IndexFilePanel extends PlainPanel<FilePath>
{
    public IndexFilePanel(String id, IModel<FilePath> model)
    {
        super(id, model);
    }

    @Override
    protected Component createHeading(String id)
    {
        return new PanelHeading(id, new AbstractPathNameModel(this.getModel()), Heading.Level.H2);
    }
}
