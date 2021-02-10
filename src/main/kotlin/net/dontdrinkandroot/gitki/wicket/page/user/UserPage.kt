package net.dontdrinkandroot.gitki.wicket.page.user

import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters

abstract class UserPage<T> : DecoratorPage<T> {
    constructor(parameters: PageParameters) : super(parameters)
    constructor(model: IModel<T>) : super(model)
}