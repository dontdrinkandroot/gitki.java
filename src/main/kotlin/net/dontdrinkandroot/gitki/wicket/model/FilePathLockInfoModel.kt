package net.dontdrinkandroot.gitki.wicket.model

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.LockInfo
import net.dontdrinkandroot.gitki.service.wiki.WikiService
import net.dontdrinkandroot.wicket.model.AbstractChainedInjectedLoadableDetachableModel
import org.apache.wicket.model.IModel
import org.apache.wicket.spring.injection.annot.SpringBean

class FilePathLockInfoModel(parentModel: IModel<out FilePath>) :
    AbstractChainedInjectedLoadableDetachableModel<FilePath, LockInfo>(parentModel) {

    @SpringBean
    private lateinit var wikiService: WikiService

    override fun load(parentValue: FilePath?) = wikiService.getLockInfo(parentValue!!)
}