package net.dontdrinkandroot.gitki.wicket.component.button

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage
import net.dontdrinkandroot.gitki.wicket.security.Render
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.component.button.BookmarkablePageButton
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome5IconClass
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters

@Render(Role.COMMITTER)
class EditButton(id: String, model: IModel<FilePath>) :
    BookmarkablePageButton<FilePath>(id, pageClass = EditPage::class.java, buttonStyleModel = Model(null)) {

    init {
        this.model = model
        this.add(IconBehavior(FontAwesome5IconClass.PEN.createIcon().apply { fixedWidth = true }))
    }

    override fun getPageParameters(): PageParameters {
        val pageParameters = PageParameterUtils.from(this.modelObject)
        pageParameters.add("action", "edit")
        return pageParameters
    }
}