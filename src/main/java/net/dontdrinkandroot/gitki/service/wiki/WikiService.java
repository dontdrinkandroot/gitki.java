package net.dontdrinkandroot.gitki.service.wiki;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface WikiService
{
    FilePath resolveIndexFile(DirectoryPath directoryPath);
}
