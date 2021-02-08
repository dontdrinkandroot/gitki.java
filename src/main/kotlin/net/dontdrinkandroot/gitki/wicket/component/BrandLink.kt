package net.dontdrinkandroot.gitki.wicket.component

import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService
import net.dontdrinkandroot.gitki.wicket.getGitkiApplication
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.model.Model
import org.apache.wicket.spring.injection.annot.SpringBean

class BrandLink(id: String) : BookmarkablePageLink<Void>(id, getGitkiApplication().homePage) {

    @SpringBean
    private val configurationService: ConfigurationService? = null

    init {
        this.body = Model.of(configurationService!!.name)
    }
}