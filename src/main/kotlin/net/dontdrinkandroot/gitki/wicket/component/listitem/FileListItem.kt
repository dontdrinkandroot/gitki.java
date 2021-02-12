package net.dontdrinkandroot.gitki.wicket.component.listitem

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.component.FileActionsDropdownButton
import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathBasicFileAttributesModel
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathNameModel
import net.dontdrinkandroot.gitki.wicket.model.FileSizeStringModel
import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel
import net.dontdrinkandroot.gitki.wicket.page.file.view.SimpleViewPage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.css.*
import net.dontdrinkandroot.wicket.model.model
import net.dontdrinkandroot.wicket.model.nio.file.attribute.BasicFileAttributesLastModifiedTimeModel
import net.dontdrinkandroot.wicket.model.nio.file.attribute.BasicFileAttributesSizeModel
import net.dontdrinkandroot.wicket.model.nio.file.attribute.FileTimeInstantModel
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model

class FileListItem(id: String, model: IModel<FilePath>) : GenericPanel<FilePath>(id, model) {

    private val attributesModel = AbstractPathBasicFileAttributesModel(model)

    override fun onInitialize() {
        super.onInitialize()
        val actionsDropDownButton = FileActionsDropdownButton(
            "actions",
            this.model,
            buttonStyleModel = Model(null),
            buttonSizeModel = ButtonSize.SMALL.model(),
            prependIconModel = FontAwesome5IconClass.ELLIPSIS_V.createIcon().apply { fixedWidth = true }.model()
        )
        actionsDropDownButton.setDropdownAlignment(DropdownAlignment.LEFT)
        this.add(actionsDropDownButton)

        val link: BookmarkablePageLink<Void> = BookmarkablePageLink(
            "link",
            SimpleViewPage::class.java,
            PageParameterUtils.from(this.modelObject)
        )
        link.body = AbstractPathNameModel(this.model)
        this.add(link)

        val sizeLabel = Label("size", FileSizeStringModel(BasicFileAttributesSizeModel(attributesModel)))
        this.add(sizeLabel)

        val lastModifiedLabel = Label(
            "lastmodified",
            TemporalAccessorStringModel(FileTimeInstantModel(BasicFileAttributesLastModifiedTimeModel(attributesModel)))
        )
        this.add(lastModifiedLabel)

        this.add(CssClassAppender(GitkiCssClass.FILE))
    }
}