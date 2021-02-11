package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.wicket.head.AppCssHeaderItem
import net.dontdrinkandroot.wicket.bootstrap.page.BootstrapPage
import org.apache.wicket.Component
import org.apache.wicket.markup.head.HeaderItem
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters

abstract class ScaffoldPage<T> : BootstrapPage<T> {

    private lateinit var titleModel: IModel<String?>

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<T>) : super(model)

    override fun createPageTitle(id: String): Component {
        titleModel = createTitleModel()
        return Label(id, titleModel)
    }

    override val bootstrapHeaderItem: HeaderItem
        get() = AppCssHeaderItem()

    protected abstract fun createTitleModel(): IModel<String?>
}