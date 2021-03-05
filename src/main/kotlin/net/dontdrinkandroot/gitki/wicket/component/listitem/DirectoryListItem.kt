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
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import net.dontdrinkandroot.wicket.kmodel.kModel
import net.dontdrinkandroot.wicket.kmodel.model
import net.dontdrinkandroot.wicket.markup.html.link.BookmarkablePageLink
import net.dontdrinkandroot.wicket.model.nio.file.attribute.BasicFileAttributesLastModifiedTimeModel
import net.dontdrinkandroot.wicket.model.nio.file.attribute.FileTimeInstantModel
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import java.nio.file.attribute.BasicFileAttributes

class DirectoryListItem(id: String, model: IModel<DirectoryPath>) : GenericPanel<DirectoryPath>(id, model) {

    var attributesModel: IModel<BasicFileAttributes> = AbstractPathBasicFileAttributesModel(model)

    override fun onInitialize() {
        super.onInitialize()

        this.add(
            DirectoryActionsDropdownButton(
                "actions",
                this.model,
                buttonStyleModel = Model(null),
                buttonSizeModel = model(ButtonSize.SMALL),
                prependIconModel = model(FontAwesome5IconClass.ELLIPSIS_V.createIcon(fixedWidth = true))
            )
        )

        this.add(
            BookmarkablePageLink<Void>(
                "link",
                pageClass = DirectoryPage::class.java,
                pageParameters = PageParameterUtils.from(this.modelObject),
                bodyModel = AbstractPathNameModel(this.kModel)
            )
        )

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

        this.add(CssClassAppender(GitkiCssClass.DIRECTORY))
    }
}