package net.dontdrinkandroot.gitki.wicket.event;

import net.dontdrinkandroot.gitki.model.FilePath;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileMovedEvent extends FileEvent
{
    private FilePath targetPath;

    public FileMovedEvent(FilePath filePath, AjaxRequestTarget target, FilePath targetPath)
    {
        super(filePath, target);
        this.targetPath = targetPath;
    }

    public FilePath getTargetPath()
    {
        return this.targetPath;
    }
}
