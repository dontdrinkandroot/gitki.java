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
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonSize
import net.dontdrinkandroot.wicket.kmodel.kModel
import net.dontdrinkandroot.wicket.markup.html.link.BookmarkablePageLink
import net.dontdrinkandroot.wicket.model.nio.file.attribute.BasicFileAttributesLastModifiedTimeModel
import net.dontdrinkandroot.wicket.model.nio.file.attribute.BasicFileAttributesSizeModel
import net.dontdrinkandroot.wicket.model.nio.file.attribute.FileTimeInstantModel
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel

class FileListItem(id: String, model: IModel<FilePath>) : GenericPanel<FilePath>(id, model) {

    private val attributesModel = AbstractPathBasicFileAttributesModel(model)

    override fun onInitialize() {
        super.onInitialize()

        this.add(
            FileActionsDropdownButton(
                "actions",
                this.model,
                buttonSize = ButtonSize.SMALL
            )
        )

        this.add(
            BookmarkablePageLink<Void>(
                "link",
                pageClass = SimpleViewPage::class.java,
                pageParameters = PageParameterUtils.from(this.modelObject),
                bodyModel = AbstractPathNameModel(this.kModel)
            )
        )

        this.add(Label("size", FileSizeStringModel(BasicFileAttributesSizeModel(attributesModel))))

        this.add(
            Label(
                "lastmodified",
                TemporalAccessorStringModel(
                    FileTimeInstantModel(
                        BasicFileAttributesLastModifiedTimeModel(
                            attributesModel
                        )
                    )
                )
            )
        )

        this.add(CssClassAppender(GitkiCssClass.FILE))
    }
}