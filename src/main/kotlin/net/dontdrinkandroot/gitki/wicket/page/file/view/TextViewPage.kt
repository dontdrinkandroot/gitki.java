package net.dontdrinkandroot.gitki.wicket.page.file.view

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.component.button.EditButton
import net.dontdrinkandroot.gitki.wicket.component.button.PrintButton
import net.dontdrinkandroot.gitki.wicket.headeritem.HighlightInitHeaderItem
import net.dontdrinkandroot.gitki.wicket.model.FilePathStringContentModel
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters

class TextViewPage : ViewPage {

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<FilePath>) : super(model)

    override fun onInitialize() {
        super.onInitialize()
        this.add(Label("content", FilePathStringContentModel(this.model)))
    }

    override fun renderHead(response: IHeaderResponse) {
        super.renderHead(response)
        response.render(HighlightInitHeaderItem())
    }

    override fun populatePrimaryButtons(view: RepeatingView) {
        view.add(EditButton(view.newChildId(), this.model))
        view.add(PrintButton(view.newChildId()))
        super.populatePrimaryButtons(view)
    }
}