package net.dontdrinkandroot.gitki.wicket.event

import net.dontdrinkandroot.gitki.model.DirectoryPath
import org.apache.wicket.ajax.AjaxRequestTarget

class DirectoryMovedEvent(directoryPath: DirectoryPath, target: AjaxRequestTarget?, val targetPath: DirectoryPath) :
    DirectoryEvent(directoryPath, target)