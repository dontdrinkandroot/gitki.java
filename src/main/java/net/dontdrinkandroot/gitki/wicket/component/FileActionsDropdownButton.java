package net.dontdrinkandroot.gitki.wicket.component;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.component.item.EditFileItem;
import net.dontdrinkandroot.gitki.wicket.component.item.RemoveFileModalItem;
import net.dontdrinkandroot.wicket.bootstrap.component.button.DropdownButton;
import net.dontdrinkandroot.wicket.bootstrap.css.DropdownAlignment;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@AuthorizeAction(action = Action.ENABLE, roles = Role.Constants.COMMITTER)
public class FileActionsDropdownButton extends DropdownButton<FilePath>
{
    public FileActionsDropdownButton(String id, IModel<FilePath> model)
    {
        super(id, model, null);
        this.setDropdownAlignment(DropdownAlignment.RIGHT);
    }

    @Override
    protected void populateItems(RepeatingView itemView)
    {
        itemView.add(new EditFileItem(itemView.newChildId(), this.getModel()));
        itemView.add(new RemoveFileModalItem(itemView.newChildId(), this.getModel()));
    }
}
