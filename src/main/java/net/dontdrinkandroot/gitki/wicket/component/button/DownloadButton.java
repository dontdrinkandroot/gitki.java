package net.dontdrinkandroot.gitki.wicket.component.button;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.SharedResourceReference;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DownloadButton extends ResourceLink<FilePath>
{
    public DownloadButton(String id, IModel<FilePath> model)
    {
        super(id, new SharedResourceReference("raw"), PageParameterUtils.from(model.getObject()));
        this.setModel(model);
        this.add(new ButtonBehavior());
        this.add(new IconBehavior(FontAwesomeIconClass.DOWNLOAD.createIcon().setFixedWidth(true)));
    }
}
