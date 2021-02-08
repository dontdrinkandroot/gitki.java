package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.model.IModel
import java.io.IOException
import javax.inject.Inject

class FilePathStringContentModel(parentModel: IModel<out FilePath>) :
    AbstractChainedInjectedLoadableDetachableModel<FilePath, String>(parentModel) {

    @Inject
    private lateinit var gitService: GitService

    override fun load() = try {
        gitService.getContentAsString(this.parentObject!!)
    } catch (e: IOException) {
        throw WicketRuntimeException(e)
    }
}