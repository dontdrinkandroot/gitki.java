package net.dontdrinkandroot.gitki.wicket.css;

import net.dontdrinkandroot.wicket.css.CssClass;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public enum GitkiCssClass implements CssClass
{
    CARET_OFF("caret-off"),
    DIRECTORY("directory"),
    FILE("file"),
    FLASH_MESSAGES("flash-messages"),
    LOCK_INFO("lock-info");

    private String classString;

    GitkiCssClass(String classString)
    {
        this.classString = classString;
    }

    @Override
    public String getClassString()
    {
        return this.classString;
    }
}
