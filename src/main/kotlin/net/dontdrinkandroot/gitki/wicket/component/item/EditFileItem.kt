package net.dontdrinkandroot.gitki.wicket.component.item

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass
import org.apache.wicket.model.IModel
import org.apache.wicket.model.StringResourceModel

@Render(Role.COMMITTER)
class EditFileItem(id: String, filePathModel: IModel<FilePath>) :
    BookmarkablePageLinkItem<Void?>(id, StringResourceModel("gitki.edit"), EditPage::class.java) {

    init {
        val pageParameters = PageParameterUtils.from(filePathModel.getObject())
        pageParameters.add("action", "edit")
        parameters = pageParameters
        setPrependIcon(FontAwesomeIconClass.PENCIL.createIcon().setFixedWidth(true))
    }
}