package net.dontdrinkandroot.gitki.wicket.css

import net.dontdrinkandroot.wicket.css.CssClass

enum class GitkiCssClass(override val classString: String) : CssClass {
    BTN_ICON("gk-btn-icon"),
    DIRECTORY("directory"),
    FILE("file"),
    FLASH_MESSAGES("flash-messages"),
    LOCK_INFO("lock-info");
}