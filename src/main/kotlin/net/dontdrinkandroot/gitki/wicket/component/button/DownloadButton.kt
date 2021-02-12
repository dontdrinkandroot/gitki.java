package net.dontdrinkandroot.gitki.wicket.component.button

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.bootstrap.behavior.ButtonBehavior
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesome4IconClass
import org.apache.wicket.markup.html.link.ResourceLink
import org.apache.wicket.model.IModel
import org.apache.wicket.request.resource.SharedResourceReference

class DownloadButton(id: String, model: IModel<FilePath>) :
    ResourceLink<FilePath>(id, SharedResourceReference("raw"), PageParameterUtils.from(model.getObject())) {

    init {
        this.model = model
        this.add(ButtonBehavior())
        this.add(IconBehavior(FontAwesome4IconClass.DOWNLOAD.createIcon().apply { fixedWidth = true }))
    }
}