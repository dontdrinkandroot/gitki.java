package net.dontdrinkandroot.gitki.service.git;

import net.dontdrinkandroot.gitki.config.GitkiConfigurationProperties;
import net.dontdrinkandroot.gitki.model.AbstractPath;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.User;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
public class DefaultGitService implements GitService
{
    private Path basePath;

    private Path gitPath = Paths.get(".git");

    private Git git;

    protected DefaultGitService()
    {
        /* RI */
    }

    @Inject
    public DefaultGitService(GitkiConfigurationProperties configurationProperties) throws IOException
    {
        if (!StringUtils.endsWith(configurationProperties.getRepository(), File.separator)) {
            throw new RuntimeException("Git Dir must end with " + File.separator);
        }

        Path gitPath = Paths.get(configurationProperties.getRepository());
        if (!gitPath.isAbsolute()) {
            throw new RuntimeException("Repository path must be absolute");
        }

        this.basePath = gitPath;

        Repository repository = FileRepositoryBuilder.create(this.basePath.resolve(".git").toFile());
        if (repository.findRef("HEAD") == null) {
            repository.create();
        }

        this.git = new Git(repository);
    }

    @Override
    public Path getRepositoryPath()
    {
        return this.basePath;
    }

    @Override
    public List<AbstractPath> listDirectory(DirectoryPath directoryPath) throws IOException
    {
        Path absolutePath = this.resolveAbsolutePath(directoryPath, true);

        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(absolutePath);

        List<AbstractPath> entries = new ArrayList<>();
        for (Path subPath : directoryStream) {
            Path relativeSubPath = this.basePath.relativize(subPath);
            if (!relativeSubPath.startsWith(".git")) {
                if (Files.isDirectory(subPath)) {
                    entries.add(DirectoryPath.from(relativeSubPath));
                } else {
                    entries.add(FilePath.from(relativeSubPath));
                }
            }
        }

        entries.sort(AbstractPath::compareTo);

        return entries;
    }

    @Override
    public List<DirectoryPath> listAllDirectories() throws IOException
    {
        List<DirectoryPath> directories = new ArrayList<>();
        Files.walkFileTree(
                this.basePath, new AbstractDirectoryVisitor()
                {
                    @Override
                    protected void visitDirectory(Path dir, BasicFileAttributes attrs)
                    {
                        Path relativePath = DefaultGitService.this.basePath.relativize(dir);
                        if (DefaultGitService.this.isValid(relativePath)) {
                            directories.add(DirectoryPath.from(relativePath));
                        }
                    }
                });

        return directories;
    }

    @Override
    public boolean exists(AbstractPath path)
    {
        Path absolutePath = this.resolveAbsolutePath(path);
        if (path.isDirectoryPath()) {
            return Files.exists(absolutePath) && Files.isDirectory(absolutePath);
        } else {
            return Files.exists(absolutePath) && Files.isRegularFile(absolutePath);
        }
    }

    @Override
    public DirectoryPath findExistingDirectoryPath(AbstractPath path)
    {
        DirectoryPath directoryPath;
        if (path.isFilePath()) {
            directoryPath = path.getParent();
        } else {
            directoryPath = (DirectoryPath) path;
        }

        do {
            if (this.exists(directoryPath)) {
                return directoryPath;
            }
            directoryPath = directoryPath.getParent();
        } while (!directoryPath.isRoot());

        return new DirectoryPath();
    }

    @Override
    public byte[] getContent(FilePath filePath) throws IOException
    {
        Path fullPath = this.resolveAbsolutePath(filePath, true);

        return Files.readAllBytes(fullPath);
    }

    @Override
    public String getContentAsString(FilePath filePath) throws IOException
    {
        byte[] bytes = this.getContent(filePath);

        return new String(bytes);
    }

    @Override
    public void addAndCommit(
            FilePath filePath,
            String content,
            User user,
            String commitMessage
    ) throws IOException, GitAPIException
    {
        this.add(filePath, content.getBytes());
        this.commit(user, commitMessage);
    }

    @Override
    public void add(FilePath filePath, byte[] content) throws IOException, GitAPIException
    {
        this.createDirectory(filePath.getParent());
        Path absolutePath = this.resolveAbsolutePath(filePath);
        Files.write(absolutePath, content);
        this.git.add().addFilepattern(filePath.toString()).call();
    }

    @Override
    public void moveAndCommit(
            FilePath filePath,
            DirectoryPath targetPath,
            User user,
            String commitMessage
    ) throws IOException, GitAPIException
    {
        FilePath targetFilePath = targetPath.appendFileName(filePath.getName());
        Path absoluteSource = this.resolveAbsolutePath(filePath);
        Path absoluteTarget = this.resolveAbsolutePath(targetFilePath);
        Files.move(absoluteSource, absoluteTarget, StandardCopyOption.ATOMIC_MOVE);
        this.git.add().addFilepattern(targetFilePath.toString()).call();
        this.git.rm().addFilepattern(filePath.toString()).call();
        this.commit(user, commitMessage);
    }

    @Override
    public void createDirectory(DirectoryPath directoryPath) throws IOException
    {
        Path fullPath = this.resolveAbsolutePath(directoryPath);
        Files.createDirectories(fullPath);
    }

    @Override
    public void removeAndCommit(FilePath filePath, User user, String commitMessage) throws IOException, GitAPIException
    {
        this.git.rm().addFilepattern(filePath.toString()).call();
        this.commit(user, commitMessage);
    }

    @Override
    public void commit(User user, String commitMessage) throws GitAPIException
    {
        this.git.commit().setMessage(commitMessage).setAuthor(user.getFullName(), user.getEmail()).call();
    }

    @Override
    public Path resolveAbsolutePath(AbstractPath path)
    {
        Path relativePath = path.toPath();
        if (relativePath.startsWith(".git")) {
            throw new RuntimeException("Cannot access .git directory");
        }

        Path absolutePath = this.basePath.resolve(relativePath);
        if (!absolutePath.startsWith(this.basePath)) {
            throw new RuntimeException("Trying to access path outside of repository");
        }

        return absolutePath;
    }

    @Override
    public Path resolveAbsolutePath(AbstractPath path, boolean mustExist) throws FileNotFoundException
    {
        Path absolutePath = this.resolveAbsolutePath(path);

        if (mustExist && !Files.exists(absolutePath)) {
            throw new FileNotFoundException(String.format("%s does not exist", absolutePath.toString()));
        }

        return absolutePath;
    }

    @Override
    public BasicFileAttributes getBasicFileAttributes(AbstractPath path) throws IOException
    {
        Path absolutePath = this.resolveAbsolutePath(path, true);
        return Files.readAttributes(absolutePath, BasicFileAttributes.class);
    }

    @Override
    public long getRevisionCount() throws GitAPIException
    {
        try {
            Iterable<RevCommit> commits = this.git.log().call();
            long count = 0;
            for (RevCommit commit : commits) {
                count++;
            }

            return count;
        } catch (NoHeadException e) {
            /* No HEAD exists and therefore no history */
            return 0;
        }
    }

    @Override
    public Iterator<? extends RevCommit> getRevisionIterator(long first, long count) throws GitAPIException
    {
        return this.git.log().setSkip((int) first).setMaxCount((int) count).call().iterator();
    }

    private boolean isValid(Path relativePath)
    {
        return !relativePath.startsWith(this.gitPath);
    }
}
