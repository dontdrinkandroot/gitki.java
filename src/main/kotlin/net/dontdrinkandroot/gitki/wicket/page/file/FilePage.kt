package net.dontdrinkandroot.gitki.wicket.page.file

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.page.BrowsePage
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.kmodel.KModel
import org.apache.wicket.model.Model
import org.apache.wicket.request.mapper.parameter.PageParameters

open class FilePage : BrowsePage<FilePath> {

    constructor(parameters: PageParameters) : super(parameters) {
        val path = PageParameterUtils.toFilePath(parameters)
        this.model = Model.of(path)
    }

    constructor(model: KModel<FilePath>) : super(model) {
        PageParameterUtils.from(model.getObject(), pageParameters)
    }
}