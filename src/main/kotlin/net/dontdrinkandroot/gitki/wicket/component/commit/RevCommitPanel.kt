package net.dontdrinkandroot.gitki.wicket.component.commit

import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel
import net.dontdrinkandroot.wicket.markup.html.basic.addLabel
import net.dontdrinkandroot.wicket.model.chain
import net.dontdrinkandroot.wicket.model.function
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.revwalk.RevCommit

class RevCommitPanel(id: String, model: IModel<RevCommit>, vararg behaviors: Behavior) :
    GenericPanel<RevCommit>(id, model) {

    init {
        val authorIdentModel = this.model.function(RevCommit::getAuthorIdent)
        val authorDateModel = authorIdentModel.function(PersonIdent::getWhen).chain({ it?.toInstant() })
        addLabel("fullMessage", model.function(RevCommit::getFullMessage))
        addLabel("authorName", authorIdentModel.function(PersonIdent::getName))
        addLabel("authorEmail", authorIdentModel.function(PersonIdent::getEmailAddress))
        addLabel("authorDate", TemporalAccessorStringModel(authorDateModel))
        behaviors.forEach { add(it) }
    }
}