package net.dontdrinkandroot.gitki.wicket.page.file.edit;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.LockInfo;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.lock.LockedException;
import net.dontdrinkandroot.gitki.service.wiki.WikiService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass;
import net.dontdrinkandroot.gitki.wicket.model.FilePathLockInfoModel;
import net.dontdrinkandroot.gitki.wicket.model.TemporalAccessorStringModel;
import net.dontdrinkandroot.gitki.wicket.model.lockinfo.LockInfoExpiryModel;
import net.dontdrinkandroot.gitki.wicket.model.lockinfo.LockInfoUserModel;
import net.dontdrinkandroot.gitki.wicket.page.file.FilePage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import net.dontdrinkandroot.wicket.bootstrap.css.TextAlignment;
import net.dontdrinkandroot.wicket.model.ToStringModel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
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

    protected Label lockLabel;

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
            LockInfo lockInfo = this.wikiService.lock(this.getModelObject(), GitkiWebSession.get().getUser());
        } catch (LockedException e) {
            //TODO: Add message
            throw new AbortWithHttpErrorCodeException(423);
        }
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        IModel<LockInfo> lockInfoModel = new FilePathLockInfoModel(this.getModel());
        StringResourceModel lockLabelModel = new StringResourceModel("gitki.locked").setParameters(
                new ToStringModel(new LockInfoUserModel(lockInfoModel)),
                new TemporalAccessorStringModel(new LockInfoExpiryModel(lockInfoModel))
        );
        this.lockLabel = new Label("lockInfo", lockLabelModel);
        this.lockLabel.setOutputMarkupId(true);
        this.lockLabel.add(new CssClassAppender(GitkiCssClass.LOCK_INFO));
        this.lockLabel.add(new IconBehavior(FontAwesomeIconClass.LOCK.createIcon()));
        this.lockLabel.add(new CssClassAppender(TextAlignment.CENTER));
        this.add(this.lockLabel);
    }

    protected WikiService getWikiService()
    {
        return this.wikiService;
    }
}
