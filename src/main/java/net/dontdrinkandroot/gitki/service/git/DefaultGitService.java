package net.dontdrinkandroot.gitki.service.git;

import net.dontdrinkandroot.gitki.model.DirectoryListing;
import net.dontdrinkandroot.gitki.model.DirectoryPath;
import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.User;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DefaultGitService implements GitService
{
    private Path basePath;

    private Git git;

    protected DefaultGitService()
    {
        /* RI */
    }

    public DefaultGitService(String gitDir) throws IOException
    {
        if (!StringUtils.endsWith(gitDir, File.separator)) {
            throw new RuntimeException("Git Dir must end with " + File.separator);
        }

        Path gitPath = Paths.get(gitDir);
        if (!gitPath.isAbsolute()) {
            throw new RuntimeException("Repository path must be absolute");
        }

        this.basePath = gitPath;

        Repository repository = FileRepositoryBuilder.create(this.basePath.resolve(".git").toFile());
        if (repository.getRef("HEAD") == null) {
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
    public DirectoryListing listDirectory(Path path) throws IOException
    {
        if (path.isAbsolute()) {
            throw new RuntimeException("Path must not be absolute");
        }

        Path directoryPath = this.resolve(path, true);

        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath);

        List<DirectoryPath> subDirectories = new ArrayList<>();
        List<FilePath> files = new ArrayList<>();
        for (Path subPath : directoryStream) {
            Path relativePath = this.basePath.relativize(subPath);
            if (!relativePath.startsWith(".git")) {
                if (Files.isDirectory(subPath)) {
                    subDirectories.add(DirectoryPath.from(relativePath));
                } else {
                    files.add(FilePath.from(relativePath));
                }
            }
        }

        return new DirectoryListing(subDirectories, files);
    }

    @Override
    public byte[] getContent(Path path) throws IOException
    {
        Path fullPath = this.resolve(path, true);

        return Files.readAllBytes(fullPath);
    }

    @Override
    public String getContentAsString(Path path) throws IOException
    {
        byte[] bytes = this.getContent(path);

        return new String(bytes);
    }

    @Override
    public void addAndCommit(
            Path path,
            String content,
            User user,
            String commitMessage
    ) throws IOException, GitAPIException
    {
        this.add(path, content.getBytes());
        this.commit(user, commitMessage);
    }

    @Override
    public void add(Path path, byte[] content) throws IOException, GitAPIException
    {
        Path fullPath = this.resolve(path);
        Files.write(fullPath, content);
        this.git.add().addFilepattern(path.toString()).call();
    }

    @Override
    public void createDirectory(Path path) throws IOException
    {
        Path fullPath = this.resolve(path);
        Files.createDirectories(fullPath);
    }

    @Override
    public void removeAndCommit(Path path, User user, String commitMessage) throws IOException, GitAPIException
    {
        this.git.rm().addFilepattern(path.toString()).call();
        this.commit(user, commitMessage);
    }

    @Override
    public void commit(User user, String commitMessage) throws GitAPIException
    {
        this.git.commit().setMessage(commitMessage).setAuthor(user.getFullName(), user.getEmail()).call();
    }

    @Override
    public Path resolve(Path path) throws FileNotFoundException
    {
        return this.resolve(path, false);
    }

    @Override
    public Path resolve(Path path, boolean mustExist) throws FileNotFoundException
    {
        if (path.startsWith(".git")) {
            throw new RuntimeException("Cannot access .git directory");
        }

        Path resolvedPath = this.basePath.resolve(path);
        if (!resolvedPath.startsWith(this.basePath)) {
            throw new RuntimeException("Trying to access path outside of repository");
        }

        if (mustExist && !Files.exists(resolvedPath)) {
            throw new FileNotFoundException(String.format("%s does not exist", resolvedPath.toString()));
        }

        return resolvedPath;
    }

    @Override
    public BasicFileAttributes getBasicFileAttributes(Path path) throws IOException
    {
        Path absolutePath = this.resolve(path, true);
        return Files.readAttributes(absolutePath, BasicFileAttributes.class);
    }

    @Override
    public long getRevisionCount() throws GitAPIException
    {
        Iterable<RevCommit> commits = this.git.log().call();
        long count = 0;
        for (RevCommit commit : commits) {
            count++;
        }

        return count;
    }

    @Override
    public Iterator<? extends RevCommit> getRevisionIterator(long first, long count) throws GitAPIException
    {
        return this.git.log().setSkip((int) first).setMaxCount((int) count).call().iterator();
    }
}
