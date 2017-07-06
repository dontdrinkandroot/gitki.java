package net.dontdrinkandroot.gitki.service.configuration;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DefaultConfigurationService implements ConfigurationService
{
    private String name;

    private boolean anonymousBrowsingEnabled;

    protected DefaultConfigurationService()
    {
        /* Reflection Instantiation */
    }

    public DefaultConfigurationService(String name, boolean anonymousBrowsingEnabled)
    {
        this.name = name;
        this.anonymousBrowsingEnabled = anonymousBrowsingEnabled;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean isAnonymousBrowsingEnabled()
    {
        return this.anonymousBrowsingEnabled;
    }

    public void setAnonymousBrowsingEnabled(boolean anonymousBrowsingEnabled)
    {
        this.anonymousBrowsingEnabled = anonymousBrowsingEnabled;
    }
}
