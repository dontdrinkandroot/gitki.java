package net.dontdrinkandroot.gitki.wicket.page.file.edit

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.LockInfo
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.lock.LockedException
import net.dontdrinkandroot.gitki.service.wiki.WikiService
import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import net.dontdrinkandroot.gitki.wicket.model.FilePathLockInfoModel
import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel
import net.dontdrinkandroot.gitki.wicket.model.lockinfo.LockInfoExpiryModel
import net.dontdrinkandroot.gitki.wicket.model.lockinfo.LockInfoUserModel
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage
import net.dontdrinkandroot.gitki.wicket.security.Instantiate
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass
import net.dontdrinkandroot.wicket.bootstrap.css.TextAlignment
import net.dontdrinkandroot.wicket.model.ToStringModel
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.apache.wicket.model.StringResourceModel
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.spring.injection.annot.SpringBean

@Instantiate(Role.COMMITTER)
open class EditPage : FilePage {

    @SpringBean
    protected lateinit var wikiService: WikiService

    protected lateinit var lockLabel: Label

    constructor(parameters: PageParameters) : super(parameters) {
        lock()
    }

    constructor(model: IModel<FilePath>) : super(model) {
        pageParameters["action"] = "edit"
        lock()
    }

    private fun lock() {
        try {
            val lockInfo = wikiService.lock(this.modelObject, getGitkiSession().user!!)
        } catch (e: LockedException) {
            //TODO: Add message
            throw AbortWithHttpErrorCodeException(423)
        }
    }

    override fun onInitialize() {
        super.onInitialize()
        val lockInfoModel: IModel<LockInfo> = FilePathLockInfoModel(this.model)
        val lockLabelModel = StringResourceModel("gitki.locked").setParameters(
            ToStringModel(LockInfoUserModel(lockInfoModel)),
            TemporalAccessorStringModel(LockInfoExpiryModel(lockInfoModel))
        )
        lockLabel = Label("lockInfo", lockLabelModel)
        lockLabel.outputMarkupId = true
        lockLabel.add(CssClassAppender(GitkiCssClass.LOCK_INFO))
        lockLabel.add(IconBehavior(FontAwesomeIconClass.LOCK.createIcon()))
        lockLabel.add(CssClassAppender(TextAlignment.CENTER))
        this.add(lockLabel)
    }
}