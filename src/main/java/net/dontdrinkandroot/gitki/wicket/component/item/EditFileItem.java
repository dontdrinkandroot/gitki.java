package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import net.dontdrinkandroot.wicket.bootstrap.component.item.BookmarkablePageLinkItem;
import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.COMMITTER)
public class EditFileItem extends BookmarkablePageLinkItem
{
    public <C extends Page> EditFileItem(String id, IModel<FilePath> filePathModel)
    {
        super(id, Model.of("Edit"), EditPage.class);
        PageParameters pageParameters = PageParameterUtils.from(filePathModel.getObject());
        pageParameters.add("action", "edit");
        this.setParameters(pageParameters);
    }
}
