package net.dontdrinkandroot.gitki.wicket.component.listitem;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.FileActionsDropDownButton;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize;
import net.dontdrinkandroot.wicket.bootstrap.css.DropDownAlignment;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
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

        this.add(new IconBehavior(FontAwesomeIconClass.FILE_O.createIcon()));

        FileActionsDropDownButton actionsDropDownButton = new FileActionsDropDownButton("actions", this.getModel());
        actionsDropDownButton.setButtonSize(ButtonSize.EXTRA_SMALL);
        actionsDropDownButton.setDropDownAlignment(DropDownAlignment.LEFT);
        this.add(actionsDropDownButton);

        BookmarkablePageLink<Void> link =
                new BookmarkablePageLink<Void>(
                        "link",
                        SimpleViewPage.class,
                        PageParameterUtils.from(this.getModelObject())
                );
        link.setBody(new AbstractPathNameModel(this.getModel()));
        this.add(link);
    }
}
