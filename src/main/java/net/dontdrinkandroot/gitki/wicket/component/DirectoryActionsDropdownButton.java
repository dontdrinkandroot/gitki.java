package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.component.item.CreateDirectoryModalItem;
import net.dontdrinkandroot.gitki.wicket.component.item.CreateFileModalItem;
import net.dontdrinkandroot.gitki.wicket.component.item.MoveDirectoryModalItem;
import net.dontdrinkandroot.gitki.wicket.component.item.UploadFilesModalItem;
import net.dontdrinkandroot.gitki.wicket.security.Enable;
import net.dontdrinkandroot.wicket.bootstrap.component.button.DropdownButton;
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Enable(Role.COMMITTER)
public class DirectoryActionsDropdownButton extends DropdownButton<DirectoryPath>
{
    public DirectoryActionsDropdownButton(String id, IModel<DirectoryPath> model)
    {
        super(id, model, null);
        this.setDropdownAlignment(DropdownAlignment.RIGHT);
    }

    @Override
    protected void populateItems(RepeatingView itemView)
    {
        itemView.add(new CreateFileModalItem(itemView.newChildId(), this.getModel()));
        itemView.add(new CreateDirectoryModalItem(itemView.newChildId(), this.getModel()));
        itemView.add(new UploadFilesModalItem(itemView.newChildId(), this.getModel()));
        itemView.add(new MoveDirectoryModalItem(itemView.newChildId(), this.getModel()));
    }
}
