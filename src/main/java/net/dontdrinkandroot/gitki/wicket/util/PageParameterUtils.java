package net.dontdrinkandroot.gitki.wicket.util;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import org.apache.wicket.request.mapper.parameter.PageParameters;

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
        pageParameters.set(segmentCount, "");

        return pageParameters;
    }

    public static PageParameters from(FilePath path)
    {
        return from(path, new PageParameters());
    }

    public static PageParameters from(FilePath path, PageParameters pageParameters)
    {
        List<String> segmentNames = path.getSegmentNames();
        int segmentCount = segmentNames.size();
        for (int i = 0; i < segmentCount; i++) {
            pageParameters.set(i, segmentNames.get(i));
        }

        return pageParameters;
    }

    public static FilePath toFilePath(PageParameters parameters)
    {
        DirectoryPath directoryPath = new DirectoryPath();
        int indexedCount = parameters.getIndexedCount();
        for (int i = 0; i < indexedCount - 1; i++) {
            String name = parameters.get(i).toString();
            directoryPath = directoryPath.appendDirectoryName(name);
        }

        return directoryPath.appendFileName(parameters.get(indexedCount - 1).toString());
    }

    public static DirectoryPath toDirectoryPath(PageParameters parameters)
    {
        DirectoryPath directoryPath = new DirectoryPath();
        if (
                null == parameters
                        || (parameters.getIndexedCount() == 1 && "".equals(parameters.get(0).toString().trim()))) {
            return directoryPath;
        }

        int indexedCount = parameters.getIndexedCount();
        for (int i = 0; i < indexedCount - 1; i++) {
            String name = parameters.get(i).toString();
            directoryPath = directoryPath.appendDirectoryName(name);
        }

        return directoryPath;
    }
}
