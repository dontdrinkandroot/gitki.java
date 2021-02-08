package net.dontdrinkandroot.gitki.wicket.event

import net.dontdrinkandroot.gitki.model.DirectoryPath
import org.apache.wicket.ajax.AjaxRequestTarget

open class DirectoryEvent : AbstractEvent {

    var directoryPath: DirectoryPath
        private set

    constructor(directoryPath: DirectoryPath) {
        this.directoryPath = directoryPath
    }

    constructor(directoryPath: DirectoryPath, target: AjaxRequestTarget?) : super(target) {
        this.directoryPath = directoryPath
    }
}