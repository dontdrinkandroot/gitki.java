package net.dontdrinkandroot.gitki.wicket.event;

import net.dontdrinkandroot.gitki.model.FilePath;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileMovedEvent extends FileEvent
{
    public FileMovedEvent(FilePath filePath)
    {
        super(filePath);
    }

    public FileMovedEvent(FilePath filePath, AjaxRequestTarget target)
    {
        super(filePath, target);
    }
}
