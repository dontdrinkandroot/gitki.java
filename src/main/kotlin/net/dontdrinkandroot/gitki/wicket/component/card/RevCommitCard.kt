package net.dontdrinkandroot.gitki.wicket.component.card

import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel
import net.dontdrinkandroot.wicket.bootstrap.component.card.SimpleCard
import net.dontdrinkandroot.wicket.model.chain
import net.dontdrinkandroot.wicket.model.function
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.revwalk.RevCommit

class RevCommitCard(id: String, model: IModel<RevCommit>) :
    SimpleCard<RevCommit>(id, model, headingModel = model.function(RevCommit::getFullMessage)) {

    override fun onInitialize() {
        super.onInitialize()
        val authorIdentModel = this.model.function(RevCommit::getAuthorIdent)
        val authorDateModel = authorIdentModel.function(PersonIdent::getWhen).chain({ it?.toInstant() })
        this.add(Label("authorName", authorIdentModel.function(PersonIdent::getName)))
        this.add(Label("authorEmail", authorIdentModel.function(PersonIdent::getEmailAddress)))
        this.add(Label("authorDate", TemporalAccessorStringModel(authorDateModel)))
    }
}