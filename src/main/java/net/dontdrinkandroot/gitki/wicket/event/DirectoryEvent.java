package net.dontdrinkandroot.gitki.wicket.event;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DirectoryEvent extends AbstractEvent
{
    private DirectoryPath directoryPath;

    public DirectoryEvent(DirectoryPath directoryPath)
    {
        this.directoryPath = directoryPath;
    }

    public DirectoryEvent(DirectoryPath directoryPath, AjaxRequestTarget target)
    {
        super(target);
        this.directoryPath = directoryPath;
    }

    public DirectoryPath getDirectoryPath()
    {
        return this.directoryPath;
    }
}
