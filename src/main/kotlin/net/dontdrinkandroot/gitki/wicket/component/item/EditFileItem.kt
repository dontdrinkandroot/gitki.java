package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome4IconClass
import net.dontdrinkandroot.wicket.model.model
import org.apache.wicket.model.IModel
import org.apache.wicket.model.StringResourceModel

@Render(Role.COMMITTER)
class EditFileItem(id: String, filePathModel: IModel<FilePath>) :
    BookmarkablePageLinkItem<Void>(
        id,
        labelModel = StringResourceModel("gitki.edit"),
        pageClass = EditPage::class.java,
        pageParameters = PageParameterUtils.from(filePathModel.getObject()).add("action", "edit"),
        prependIconModel = FontAwesome4IconClass.PENCIL.createIcon().apply { fixedWidth = true }.model()
    )