package net.dontdrinkandroot.gitki.wicket.choicerenderer;

import net.dontdrinkandroot.gitki.model.FileType;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileTypeChoiceRenderer implements IChoiceRenderer<FileType>
{
    @Override
    public Object getDisplayValue(FileType fileType)
    {
        String name = fileType.name();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    @Override
    public String getIdValue(FileType fileType, int index)
    {
        return fileType.name();
    }

    @Override
    public FileType getObject(String id, IModel<? extends List<? extends FileType>> choices)
    {
        return FileType.valueOf(id);
    }
}
