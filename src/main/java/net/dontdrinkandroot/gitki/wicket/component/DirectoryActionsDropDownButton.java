package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.component.item.CreateDirectoryModalItem;
import net.dontdrinkandroot.gitki.wicket.component.item.CreateFileModalItem;
import net.dontdrinkandroot.gitki.wicket.component.item.UploadFilesModalItem;
import net.dontdrinkandroot.gitki.wicket.security.Render;
import net.dontdrinkandroot.wicket.bootstrap.component.button.DropDownButton;
import net.dontdrinkandroot.wicket.bootstrap.css.DropDownAlignment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Render(Role.COMMITTER)
public class DirectoryActionsDropDownButton extends DropDownButton<DirectoryPath>
{
    public DirectoryActionsDropDownButton(String id, IModel<DirectoryPath> model)
    {
        super(id, model, null);
        this.setDropDownAlignment(DropDownAlignment.RIGHT);
    }

    @Override
    protected void populateItems(RepeatingView itemView)
    {
        itemView.add(new CreateFileModalItem(itemView.newChildId(), this.getModel()));
        itemView.add(new CreateDirectoryModalItem(itemView.newChildId(), this.getModel()));
        itemView.add(new UploadFilesModalItem(itemView.newChildId(), this.getModel()));
    }
}
