package net.dontdrinkandroot.gitki.service.configuration;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DefaultConfigurationService implements ConfigurationService
{
    private String name;

    protected DefaultConfigurationService()
    {
        /* Reflection Instantiation */
    }

    public DefaultConfigurationService(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return this.name;
    }
}
