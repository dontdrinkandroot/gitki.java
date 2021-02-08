package net.dontdrinkandroot.gitki.wicket.component.bspanel

import net.dontdrinkandroot.gitki.wicket.model.PersonIdentWhenModel
import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel
import net.dontdrinkandroot.gitki.wicket.model.personident.PersonIdentEmailAdressModel
import net.dontdrinkandroot.gitki.wicket.model.personident.PersonIdentNameModel
import net.dontdrinkandroot.gitki.wicket.model.revcommit.RevCommitAuthorIdentModel
import net.dontdrinkandroot.gitki.wicket.model.revcommit.RevCommitFullMessageModel
import net.dontdrinkandroot.wicket.bootstrap.component.panel.SimplePanel
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.revwalk.RevCommit

class RevCommitPanel(id: String, model: IModel<RevCommit>) :
    SimplePanel<RevCommit>(id, model, RevCommitFullMessageModel(model)) {

    override fun onInitialize() {
        super.onInitialize()
        val authorIdentModel: IModel<PersonIdent> = RevCommitAuthorIdentModel(this.model)
        this.add(Label("authorName", PersonIdentNameModel(authorIdentModel)))
        this.add(Label("authorEmail", PersonIdentEmailAdressModel(authorIdentModel)))
        this.add(
            Label(
                "authorDate",
                TemporalAccessorStringModel(PersonIdentWhenModel(RevCommitAuthorIdentModel(this.model)))
            )
        )
    }
}