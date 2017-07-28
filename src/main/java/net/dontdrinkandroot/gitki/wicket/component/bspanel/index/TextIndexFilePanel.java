package net.dontdrinkandroot.gitki.wicket.component.bspanel.index;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.model.FilePathStringContentModel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class TextIndexFilePanel extends IndexFilePanel
{
    public TextIndexFilePanel(String id, IModel<FilePath> model)
    {
        super(id, model);
    }

    @Override
    protected Component createBody(String id)
    {
        return new Label(id, new FilePathStringContentModel(this.getModel()));
    }
}
