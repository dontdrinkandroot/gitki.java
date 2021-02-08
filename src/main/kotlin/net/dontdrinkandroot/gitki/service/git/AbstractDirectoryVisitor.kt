package net.dontdrinkandroot.gitki.service.git

import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes

abstract class AbstractDirectoryVisitor : SimpleFileVisitor<Path>() {

    @Throws(IOException::class)
    override fun preVisitDirectory(dir: Path, attrs: BasicFileAttributes): FileVisitResult {
        visitDirectory(dir, attrs)
        return super.preVisitDirectory(dir, attrs)
    }

    protected abstract fun visitDirectory(dir: Path, attrs: BasicFileAttributes)
}