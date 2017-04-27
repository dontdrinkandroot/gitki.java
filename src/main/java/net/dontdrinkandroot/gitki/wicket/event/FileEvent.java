package net.dontdrinkandroot.gitki.wicket.event;

import net.dontdrinkandroot.gitki.model.FilePath;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileEvent extends AbstractEvent
{
    private FilePath filePath;

    public FileEvent(FilePath filePath)
    {
        this.filePath = filePath;
    }

    public FileEvent(FilePath filePath, AjaxRequestTarget target)
    {
        super(target);
        this.filePath = filePath;
    }

    public FilePath getFilePath()
    {
        return this.filePath;
    }
}
