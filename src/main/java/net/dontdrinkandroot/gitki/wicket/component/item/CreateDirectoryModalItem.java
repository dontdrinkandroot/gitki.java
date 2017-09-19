package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.component.modal.CreateDirectoryModal;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem;
import net.dontdrinkandroot.wicket.bootstrap.css.FontAwesomeIconClass;
import net.dontdrinkandroot.wicket.bootstrap.event.CreateAndOpenModalRequest;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.COMMITTER)
public class CreateDirectoryModalItem extends AjaxLinkItem
{
    private final IModel<DirectoryPath> pathModel;

    public CreateDirectoryModalItem(String id, IModel<DirectoryPath> pathModel)
    {
        super(id, new StringResourceModel("gitki.directory.create"));
        this.pathModel = pathModel;
        this.setPrependIcon(FontAwesomeIconClass.PLUS.createIcon().setFixedWidth(true));
    }

    @Override
    protected void onClick(AjaxRequestTarget target)
    {
        this.send(
                this.getPage(),
                Broadcast.DEPTH,
                new CreateAndOpenModalRequest<>(target, CreateDirectoryModal.class, this.pathModel)
        );
    }
}
