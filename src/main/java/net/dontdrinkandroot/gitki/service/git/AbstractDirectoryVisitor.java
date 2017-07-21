package net.dontdrinkandroot.gitki.service.git;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public abstract class AbstractDirectoryVisitor extends SimpleFileVisitor<Path>
{
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
    {
        this.visitDirectory(dir, attrs);
        return super.preVisitDirectory(dir, attrs);
    }

    protected abstract void visitDirectory(Path dir, BasicFileAttributes attrs);
}
