package net.dontdrinkandroot.gitki.wicket.component.listitem;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.wicket.component.DirectoryActionsDropDownButton;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize;
import net.dontdrinkandroot.wicket.bootstrap.css.DropDownAlignment;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
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

        DirectoryActionsDropDownButton actionsDropDownButton =
                new DirectoryActionsDropDownButton("actions", this.getModel());
        actionsDropDownButton.getIconBehavior()
                .setAppendIcon(FontAwesomeIconClass.FOLDER_O.createIcon().setFixedWidth(true));
        actionsDropDownButton.setButtonSize(ButtonSize.EXTRA_SMALL);
        actionsDropDownButton.setDropDownAlignment(DropDownAlignment.LEFT);
        this.add(actionsDropDownButton);

        BookmarkablePageLink<Void> link =
                new BookmarkablePageLink<Void>(
                        "link",
                        DirectoryPage.class,
                        PageParameterUtils.from(this.getModelObject())
                );
        link.setBody(new AbstractPathNameModel(this.getModel()));
        this.add(link);
    }
}
