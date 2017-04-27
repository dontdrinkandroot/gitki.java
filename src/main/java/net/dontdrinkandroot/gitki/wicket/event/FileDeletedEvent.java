package net.dontdrinkandroot.gitki.wicket.event;

import net.dontdrinkandroot.gitki.model.FilePath;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileDeletedEvent extends FileEvent
{
    public FileDeletedEvent(FilePath filePath)
    {
        super(filePath);
    }

    public FileDeletedEvent(FilePath filePath, AjaxRequestTarget target)
    {
        super(filePath, target);
    }
}
