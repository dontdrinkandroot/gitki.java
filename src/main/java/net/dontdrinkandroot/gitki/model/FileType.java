package net.dontdrinkandroot.gitki.model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public enum FileType
{
    MARKDOWN("md"),
    TEXT("txt");

    private final String extension;

    FileType(String extension)
    {
        this.extension = extension;
    }

    public String getExtension()
    {
        return this.extension;
    }
}
