package net.dontdrinkandroot.gitki.wicket.component.button;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.behavior.IconBehavior;
import net.dontdrinkandroot.wicket.bootstrap.component.button.BookmarkablePageButton;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import org.apache.wicket.Page;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@AuthorizeAction(action = Action.RENDER, roles = Role.Constants.COMMITTER)
public class EditButton extends BookmarkablePageButton<FilePath>
{
    public <C extends Page> EditButton(String id, IModel<FilePath> model)
    {
        super(id, EditPage.class);
        this.setModel(model);
        this.add(new IconBehavior(FontAwesomeIconClass.PENCIL.createIcon()));
    }

    @Override
    public PageParameters getPageParameters()
    {
        PageParameters pageParameters = PageParameterUtils.from(this.getModelObject());
        pageParameters.add("action", "edit");

        return pageParameters;
    }
}
