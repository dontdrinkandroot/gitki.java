package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.wicket.head.AppCssHeaderItem
import net.dontdrinkandroot.wicket.bootstrap.page.BootstrapPage
import net.dontdrinkandroot.wicket.kmodel.KModel
import org.apache.wicket.Component
import org.apache.wicket.markup.head.HeaderItem
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters

abstract class ScaffoldPage<T> : BootstrapPage<T> {

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<T>) : super(model)

    override fun createPageTitle(id: String): Component = Label(id, createTitleModel())

    override val bootstrapHeaderItem: HeaderItem
        get() = AppCssHeaderItem()

    protected abstract fun createTitleModel(): KModel<String>
}