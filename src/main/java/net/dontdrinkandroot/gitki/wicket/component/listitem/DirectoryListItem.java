package net.dontdrinkandroot.gitki.wicket.component.listitem;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.wicket.component.DirectoryActionsDropdownButton;
import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathBasicFileAttributesModel;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel;
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize;
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import net.dontdrinkandroot.wicket.model.java.nio.file.attribute.BasicFileAttributesLastModifiedTimeModel;
import net.dontdrinkandroot.wicket.model.java.nio.file.attribute.FileTimeInstantModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryListItem extends GenericPanel<DirectoryPath>
{
    IModel<BasicFileAttributes> attributesModel;

    public DirectoryListItem(String id, IModel<DirectoryPath> model)
    {
        super(id, model);
        this.attributesModel = new AbstractPathBasicFileAttributesModel(model);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        DirectoryActionsDropdownButton actionsDropDownButton =
                new DirectoryActionsDropdownButton("actions", this.getModel());
        actionsDropDownButton.getIconBehavior()
                .setAppendIcon(FontAwesomeIconClass.FOLDER.createIcon().setFixedWidth(true));
        actionsDropDownButton.setButtonSize(ButtonSize.SMALL);
        actionsDropDownButton.setDropdownAlignment(DropdownAlignment.LEFT);
        this.add(actionsDropDownButton);

        BookmarkablePageLink<Void> link =
                new BookmarkablePageLink<Void>(
                        "link",
                        DirectoryPage.class,
                        PageParameterUtils.from(this.getModelObject())
                );
        link.setBody(new AbstractPathNameModel(this.getModel()));
        this.add(link);

        Label lastModifiedLabel =
                new Label(
                        "lastmodified",
                        new TemporalAccessorStringModel(new FileTimeInstantModel(new BasicFileAttributesLastModifiedTimeModel(
                                this.attributesModel)))
                );
        this.add(lastModifiedLabel);

        this.add(new CssClassAppender(GitkiCssClass.DIRECTORY));
    }
}
