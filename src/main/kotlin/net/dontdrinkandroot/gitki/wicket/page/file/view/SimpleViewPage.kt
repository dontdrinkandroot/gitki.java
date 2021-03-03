package net.dontdrinkandroot.gitki.wicket.page.file.view

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.wicket.kmodel.KModel
import org.apache.wicket.request.mapper.parameter.PageParameters

class SimpleViewPage : ViewPage {
    constructor(parameters: PageParameters) : super(parameters)
    constructor(model: KModel<FilePath>) : super(model)
}