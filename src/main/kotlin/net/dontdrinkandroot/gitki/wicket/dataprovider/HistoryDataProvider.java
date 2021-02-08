package net.dontdrinkandroot.gitki.wicket.dataprovider;

import net.dontdrinkandroot.gitki.service.git.GitService;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.Iterator;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class HistoryDataProvider implements IDataProvider<RevCommit>
{
    @SpringBean
    private GitService gitService;

    public HistoryDataProvider() {
        Injector.get().inject(this);
    }

    @Override
    public Iterator<? extends RevCommit> iterator(long first, long count) {
        try {
            return this.gitService.getRevisionIterator(first, count);
        } catch (GitAPIException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public long size() {
        try {
            return this.gitService.getRevisionCount();
        } catch (GitAPIException e) {
            throw new WicketRuntimeException(e);
        }
    }

    @Override
    public IModel<RevCommit> model(RevCommit revCommit) {
        return Model.of(revCommit);
    }

    @Override
    public void detach() {
    }
}
