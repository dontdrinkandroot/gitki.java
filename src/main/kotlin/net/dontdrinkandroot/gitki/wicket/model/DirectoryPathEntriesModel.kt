package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.model.DirectoryPath
import net.dontdrinkandroot.gitki.model.GitkiPath
import net.dontdrinkandroot.gitki.service.git.GitService
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.model.IModel
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.spring.injection.annot.SpringBean
import java.io.FileNotFoundException
import java.io.IOException

class DirectoryPathEntriesModel(parentModel: IModel<out DirectoryPath>) :
    AbstractChainedInjectedLoadableDetachableModel<DirectoryPath, List<GitkiPath>>(parentModel) {

    @SpringBean
    private lateinit var gitService: GitService

    override fun load(parentValue: DirectoryPath?) = try {
        gitService.listDirectory(parentValue!!)
    } catch (e: FileNotFoundException) {
        throw AbortWithHttpErrorCodeException(404)
    } catch (e: IOException) {
        throw WicketRuntimeException(e)
    }
}