package net.dontdrinkandroot.gitki.wicket.component.listitem;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.component.FileActionsDropdownButton;
import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathBasicFileAttributesModel;
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel;
import net.dontdrinkandroot.gitki.wicket.model.FileSizeStringModel;
import net.dontdrinkandroot.gitki.wicket.model.InstantStringModel;
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize;
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import net.dontdrinkandroot.wicket.model.java.nio.file.attribute.BasicFileAttributesLastModifiedTimeModel;
import net.dontdrinkandroot.wicket.model.java.nio.file.attribute.BasicFileAttributesSizeModel;
import net.dontdrinkandroot.wicket.model.java.nio.file.attribute.FileTimeInstantModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;

import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileListItem extends GenericPanel<FilePath>
{
    IModel<BasicFileAttributes> attributesModel;

    public FileListItem(String id, IModel<FilePath> model)
    {
        super(id, model);
        this.attributesModel = new AbstractPathBasicFileAttributesModel(model);
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        FileActionsDropdownButton actionsDropDownButton = new FileActionsDropdownButton("actions", this.getModel());
        actionsDropDownButton.getIconBehavior()
                .setAppendIcon(FontAwesomeIconClass.FILE_TEXT_O.createIcon().setFixedWidth(true));
        actionsDropDownButton.setButtonSize(ButtonSize.EXTRA_SMALL);
        actionsDropDownButton.setDropdownAlignment(DropdownAlignment.LEFT);
        this.add(actionsDropDownButton);

        BookmarkablePageLink<Void> link =
                new BookmarkablePageLink<Void>(
                        "link",
                        SimpleViewPage.class,
                        PageParameterUtils.from(this.getModelObject())
                );
        link.setBody(new AbstractPathNameModel(this.getModel()));
        this.add(link);

        Label sizeLabel =
                new Label("size", new FileSizeStringModel(new BasicFileAttributesSizeModel(this.attributesModel)));
        this.add(sizeLabel);

        Label lastModifiedLabel =
                new Label(
                        "lastmodified",
                        new InstantStringModel(
                                new FileTimeInstantModel(new BasicFileAttributesLastModifiedTimeModel(this.attributesModel))
                        )
                );
        this.add(lastModifiedLabel);

        this.add(new CssClassAppender(GitkiCssClass.FILE));
    }
}
