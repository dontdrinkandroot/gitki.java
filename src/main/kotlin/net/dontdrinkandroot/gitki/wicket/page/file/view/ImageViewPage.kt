package net.dontdrinkandroot.gitki.wicket.page.file.view

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils
import net.dontdrinkandroot.wicket.kmodel.KModel
import org.apache.wicket.markup.html.image.Image
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.SharedResourceReference

class ImageViewPage : ViewPage {
    constructor(parameters: PageParameters) : super(parameters)

    constructor(model: KModel<FilePath>) : super(model)

    override fun onInitialize() {
        super.onInitialize()
        val resourceParameters = PageParameterUtils.from(this.modelObject)
        this.add(object : Image("image", SharedResourceReference("raw"), resourceParameters) {
            override fun shouldAddAntiCacheParameter(): Boolean {
                return false
            }
        })
    }
}