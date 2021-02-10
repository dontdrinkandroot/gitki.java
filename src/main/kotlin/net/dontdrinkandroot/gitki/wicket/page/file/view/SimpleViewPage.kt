package net.dontdrinkandroot.gitki.wicket.page.file.view

import net.dontdrinkandroot.gitki.model.FilePath
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters

class SimpleViewPage : ViewPage {
    constructor(parameters: PageParameters) : super(parameters)
    constructor(model: IModel<FilePath>) : super(model)
}