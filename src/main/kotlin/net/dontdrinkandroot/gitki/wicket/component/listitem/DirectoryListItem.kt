package net.dontdrinkandroot.gitki.wicket.component.listitem

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.wicket.component.DirectoryActionsDropdownButton
import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathBasicFileAttributesModel
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel
import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel
import net.dontdrinkandroot.gitki.wicket.page.directory.DirectoryPage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass
import net.dontdrinkandroot.wicket.model.model
import net.dontdrinkandroot.wicket.model.nio.file.attribute.BasicFileAttributesLastModifiedTimeModel
import net.dontdrinkandroot.wicket.model.nio.file.attribute.FileTimeInstantModel
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import java.nio.file.attribute.BasicFileAttributes

class DirectoryListItem(id: String, model: IModel<DirectoryPath>) : GenericPanel<DirectoryPath>(id, model) {

    var attributesModel: IModel<BasicFileAttributes> = AbstractPathBasicFileAttributesModel(model)

    override fun onInitialize() {
        super.onInitialize()
        val actionsDropDownButton = DirectoryActionsDropdownButton(
            "actions",
            this.model,
            buttonStyleModel = ButtonStyle.OUTLINE_SECONDARY.model(),
            buttonSizeModel = ButtonSize.SMALL.model()
        )
        actionsDropDownButton.iconBehavior.appendIcon = FontAwesomeIconClass.FOLDER.createIcon().setFixedWidth(true)
        actionsDropDownButton.setDropdownAlignment(DropdownAlignment.LEFT)
        this.add(actionsDropDownButton)
        val link: BookmarkablePageLink<Void> = BookmarkablePageLink(
            "link",
            DirectoryPage::class.java,
            PageParameterUtils.from(this.modelObject)
        )
        link.body = AbstractPathNameModel(this.model)
        this.add(link)
        val lastModifiedLabel = Label(
            "lastmodified",
            TemporalAccessorStringModel(
                FileTimeInstantModel(
                    BasicFileAttributesLastModifiedTimeModel(
                        attributesModel
                    )
                )
            )
        )
        this.add(lastModifiedLabel)
        this.add(CssClassAppender(GitkiCssClass.DIRECTORY))
    }

}