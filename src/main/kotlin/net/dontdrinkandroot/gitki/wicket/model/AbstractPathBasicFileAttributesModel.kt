package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.model.GitkiPath
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.model.IModel
import org.apache.wicket.spring.injection.annot.SpringBean
import java.io.IOException
import java.nio.file.attribute.BasicFileAttributes

class AbstractPathBasicFileAttributesModel(parentModel: IModel<out GitkiPath>) :
    AbstractChainedInjectedLoadableDetachableModel<GitkiPath, BasicFileAttributes>(parentModel) {

    @SpringBean
    private lateinit var gitService: GitService

    override fun load(parentValue: GitkiPath?) = try {
        gitService.getBasicFileAttributes(parentValue!!)
    } catch (e: IOException) {
        throw WicketRuntimeException(e)
    }
}