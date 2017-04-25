package net.dontdrinkandroot.gitki.wicket.component.item;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.wicket.component.modal.CreateFileModal;
import net.dontdrinkandroot.wicket.bootstrap.component.item.AjaxLinkItem;
import net.dontdrinkandroot.wicket.bootstrap.event.CreateAndOpenModalRequest;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class CreateFileModalItem extends AjaxLinkItem
{
    private final IModel<DirectoryPath> pathModel;

    public CreateFileModalItem(String id, IModel<DirectoryPath> pathModel)
    {
        super(id, Model.of("Create File"));
        this.pathModel = pathModel;
    }

    @Override
    protected void onClick(AjaxRequestTarget target)
    {
        this.send(
                this.getPage(),
                Broadcast.DEPTH,
                new CreateAndOpenModalRequest<DirectoryPath>(target, CreateFileModal.class, this.pathModel)
        );
    }
}
