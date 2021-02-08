package net.dontdrinkandroot.gitki.wicket.event

import net.dontdrinkandroot.gitki.model.FilePath
import org.apache.wicket.ajax.AjaxRequestTarget

class FileDeletedEvent : FileEvent {
    constructor(filePath: FilePath) : super(filePath)
    constructor(filePath: FilePath, target: AjaxRequestTarget?) : super(filePath, target)
}