package net.dontdrinkandroot.gitki.wicket.css;

import net.dontdrinkandroot.wicket.css.CssClass;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public enum GitkiCssClass implements CssClass
{
    FILE("file"),
    DIRECTORY("directory"),
    LOCK_INFO("lock-info"),
    FLASH_MESSAGES("flash-messages");

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
