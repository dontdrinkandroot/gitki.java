package net.dontdrinkandroot.gitki.wicket.event

import org.apache.wicket.ajax.AjaxRequestTarget

open class AbstractEvent {

    var target: AjaxRequestTarget? = null
        private set

    constructor()

    constructor(target: AjaxRequestTarget?) {
        this.target = target
    }
}