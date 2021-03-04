package net.dontdrinkandroot.gitki.wicket.component.commit

import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel
import net.dontdrinkandroot.wicket.behavior.CssClassAppender
import net.dontdrinkandroot.wicket.bootstrap.component.card.Card
import net.dontdrinkandroot.wicket.bootstrap.component.card.SimpleCard
import net.dontdrinkandroot.wicket.bootstrap.css.Spacing
import net.dontdrinkandroot.wicket.component.basic.Heading
import net.dontdrinkandroot.wicket.model.chain
import net.dontdrinkandroot.wicket.model.function
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import org.eclipse.jgit.lib.PersonIdent
import org.eclipse.jgit.revwalk.RevCommit

class RevCommitPanel(id: String, model: IModel<RevCommit>, vararg behaviors: Behavior) :
    GenericPanel<RevCommit>(id, model) {

    init {
        val authorIdentModel = this.model.function(RevCommit::getAuthorIdent)
        val authorDateModel = authorIdentModel.function(PersonIdent::getWhen).chain({ it?.toInstant() })
        this.add(Label("fullMessage", model.function(RevCommit::getFullMessage)))
        this.add(Label("authorName", authorIdentModel.function(PersonIdent::getName)))
        this.add(Label("authorEmail", authorIdentModel.function(PersonIdent::getEmailAddress)))
        this.add(Label("authorDate", TemporalAccessorStringModel(authorDateModel)))
        behaviors.forEach { add(it) }
    }
}