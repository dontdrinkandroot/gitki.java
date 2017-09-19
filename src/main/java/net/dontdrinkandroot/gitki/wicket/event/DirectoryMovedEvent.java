package net.dontdrinkandroot.gitki.wicket.event;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryMovedEvent extends DirectoryEvent
{
    private final DirectoryPath targetPath;

    public DirectoryMovedEvent(DirectoryPath directoryPath, AjaxRequestTarget target, DirectoryPath targetPath)
    {
        super(directoryPath, target);
        this.targetPath = targetPath;
    }

    public DirectoryPath getTargetPath()
    {
        return this.targetPath;
    }
}
