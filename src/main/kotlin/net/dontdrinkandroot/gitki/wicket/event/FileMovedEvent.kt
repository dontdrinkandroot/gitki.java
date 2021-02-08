package net.dontdrinkandroot.gitki.wicket.event

import net.dontdrinkandroot.gitki.model.FilePath
import org.apache.wicket.ajax.AjaxRequestTarget

class FileMovedEvent(filePath: FilePath, target: AjaxRequestTarget?, val targetPath: FilePath) :
    FileEvent(filePath, target)