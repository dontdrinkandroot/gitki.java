package net.dontdrinkandroot.gitki.wicket.dataprovider

import net.dontdrinkandroot.gitki.service.git.GitService
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.injection.Injector
import org.apache.wicket.markup.repeater.data.IDataProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.model.Model
import org.apache.wicket.spring.injection.annot.SpringBean
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.revwalk.RevCommit

class HistoryDataProvider : IDataProvider<RevCommit> {

    @SpringBean
    private lateinit var gitService: GitService

    init {
        Injector.get().inject(this)
    }

    override fun iterator(first: Long, count: Long): Iterator<RevCommit> {
        return try {
            gitService.getRevisionIterator(first, count)
        } catch (e: GitAPIException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun size(): Long {
        return try {
            gitService.revisionCount
        } catch (e: GitAPIException) {
            throw WicketRuntimeException(e)
        }
    }

    override fun model(revCommit: RevCommit): IModel<RevCommit> {
        return Model.of(revCommit)
    }

    override fun detach() {}
}