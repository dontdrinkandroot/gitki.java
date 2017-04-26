package net.dontdrinkandroot.gitki.wicket.util;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class PageParameterUtils
{
    public static PageParameters from(DirectoryPath path)
    {
        return from(path, new PageParameters());
    }

    public static PageParameters from(DirectoryPath path, PageParameters pageParameters)
    {
        List<String> segmentNames = path.getSegmentNames();
        int segmentCount = segmentNames.size();
        for (int i = 0; i < segmentCount; i++) {
            pageParameters.set(i, segmentNames.get(i));
        }

        return pageParameters;
    }

    public static PageParameters from(FilePath path)
    {
        throw new NotImplementedException();
    }

    public static FilePath toFilePath(PageParameters parameters)
    {
        throw new NotImplementedException();
    }

    public static DirectoryPath toDirectoryPath(PageParameters parameters)
    {
        DirectoryPath directoryPath = new DirectoryPath();
        if (null == parameters) {
            return directoryPath;
        }

        int indexedCount = parameters.getIndexedCount();
        for (int i = 0; i < indexedCount; i++) {
            directoryPath = directoryPath.appendDirectoryName(parameters.get(i).toString());
        }

        return directoryPath;
    }
}
