package net.dontdrinkandroot.gitki.wicket.page.file.edit;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.lock.LockedException;
import net.dontdrinkandroot.gitki.service.wiki.WikiService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.COMMITTER)
public class EditPage extends FilePage
{
    @SpringBean
    private WikiService wikiService;

    public EditPage(PageParameters parameters)
    {
        super(parameters);
        this.lock();
    }

    public EditPage(IModel<FilePath> model)
    {
        super(model);
        this.getPageParameters().set("action", "edit");
        this.lock();
    }

    private void lock()
    {
        try {
            this.wikiService.lock(this.getModelObject(), GitkiWebSession.get().getUser());
        } catch (LockedException e) {
            //TODO: Add message
            throw new AbortWithHttpErrorCodeException(423);
        }
    }

    protected WikiService getWikiService()
    {
        return this.wikiService;
    }
}
