package net.dontdrinkandroot.gitki.wicket.page

import net.dontdrinkandroot.gitki.model.GitkiPath
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.gitki.wicket.component.PathBreadcrumb
import net.dontdrinkandroot.gitki.wicket.model.AbstractPathAbsoluteStringModel
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean

open class BrowsePage<T : GitkiPath> : DecoratorPage<T> {

    @SpringBean
    protected lateinit var gitService: GitService

    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: IModel<T>) : super(model)

    override fun createTitleModel(): IModel<String> {
        return AbstractPathAbsoluteStringModel(this.model)
    }

    override fun onInitialize() {
        super.onInitialize()
        val primaryButtonView = RepeatingView("primaryButton")
        populatePrimaryButtons(primaryButtonView)
        this.add(primaryButtonView)
        this.add(PathBreadcrumb("breadcrumb", model))
    }

    protected open fun populatePrimaryButtons(view: RepeatingView?) {
        /* Hook */
    }
}