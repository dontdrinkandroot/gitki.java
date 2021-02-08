package net.dontdrinkandroot.gitki.wicket.event

import net.dontdrinkandroot.gitki.model.FilePath
import org.apache.wicket.ajax.AjaxRequestTarget

open class FileEvent : AbstractEvent {

    var filePath: FilePath
        private set

    constructor(filePath: FilePath) {
        this.filePath = filePath
    }

    constructor(filePath: FilePath, target: AjaxRequestTarget?) : super(target) {
        this.filePath = filePath
    }
}