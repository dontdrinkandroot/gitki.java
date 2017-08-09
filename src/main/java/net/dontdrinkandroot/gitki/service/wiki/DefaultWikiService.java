package net.dontdrinkandroot.gitki.service.wiki;

import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.GitUser;
import net.dontdrinkandroot.gitki.model.LockInfo;
import net.dontdrinkandroot.gitki.service.git.GitService;
import net.dontdrinkandroot.gitki.service.lock.LockMissingException;
import net.dontdrinkandroot.gitki.service.lock.LockService;
import net.dontdrinkandroot.gitki.service.lock.LockedException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
public class DefaultWikiService implements WikiService
{
    private GitService gitService;

    private LockService lockService;

    private List<String> indexFiles = new ArrayList<>();

    protected DefaultWikiService()
    {
        /* Reflection Instantiation */
    }

    @Inject
    public DefaultWikiService(GitService gitService, LockService lockService)
    {
        this.gitService = gitService;
        this.lockService = lockService;
    }

    @Value("#{gitkiConfigurationProperties.indexFiles}")
    public void setIndexFiles(List<String> indexFiles)
    {
        this.indexFiles = indexFiles;
    }

    @Override
    public FilePath resolveIndexFile(DirectoryPath directoryPath)
    {
        for (String indexFile : this.indexFiles) {
            FilePath indexFilePath = directoryPath.appendFileName(indexFile);
            if (this.gitService.exists(indexFilePath)) {
                return indexFilePath;
            }
        }

        return null;
    }

    @Override
    public LockInfo lock(FilePath filePath, GitUser user) throws LockedException
    {
        return this.lockService.lock(filePath, user);
    }

    @Override
    public void save(
            FilePath filePath,
            GitUser user,
            String commitMessage,
            String content
    ) throws LockedException, LockMissingException, IOException, GitAPIException
    {
        this.lockService.lock(filePath, user);
        this.gitService.addAndCommit(filePath, content, user, commitMessage);
    }

    @Override
    public void saveAndUnlock(
            FilePath filePath,
            GitUser user,
            String commitMessage,
            String content
    ) throws LockedException, IOException, GitAPIException
    {
        this.lockService.lock(filePath, user);
        this.gitService.addAndCommit(filePath, content, user, commitMessage);
        this.lockService.release(filePath, user);
    }

    @Override
    public LockInfo getLockInfo(FilePath filePath)
    {
        return this.lockService.check(filePath);
    }
}
