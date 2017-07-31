package net.dontdrinkandroot.gitki.wicket.component.bspanel;

import net.dontdrinkandroot.gitki.wicket.model.PersonIdentWhenModel;
import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel;
import net.dontdrinkandroot.gitki.wicket.model.personident.PersonIdentEmailAdressModel;
import net.dontdrinkandroot.gitki.wicket.model.personident.PersonIdentNameModel;
import net.dontdrinkandroot.gitki.wicket.model.revcommit.RevCommitAuthorIdentModel;
import net.dontdrinkandroot.gitki.wicket.model.revcommit.RevCommitFullMessageModel;
import net.dontdrinkandroot.wicket.bootstrap.component.panel.SimplePanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class RevCommitPanel extends SimplePanel<RevCommit>
{
    public RevCommitPanel(String id, IModel<RevCommit> model)
    {
        super(id, model, new RevCommitFullMessageModel(model));
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        IModel<PersonIdent> authorIdentModel = new RevCommitAuthorIdentModel(this.getModel());
        this.add(new Label("authorName", new PersonIdentNameModel(authorIdentModel)));
        this.add(new Label("authorEmail", new PersonIdentEmailAdressModel(authorIdentModel)));
        this.add(new Label(
                "authorDate",
                new TemporalAccessorStringModel(new PersonIdentWhenModel(new RevCommitAuthorIdentModel(this.getModel())))
        ));
    }
}
