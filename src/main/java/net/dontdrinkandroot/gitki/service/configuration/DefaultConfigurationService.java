package net.dontdrinkandroot.gitki.service.configuration;

import net.dontdrinkandroot.gitki.config.GitkiConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
public class DefaultConfigurationService implements ConfigurationService
{
    private String name;

    private boolean anonymousBrowsingEnabled;

    protected DefaultConfigurationService()
    {
        /* Reflection Instantiation */
    }

    @Inject
    public DefaultConfigurationService(GitkiConfigurationProperties configurationProperties)
    {
        this.name = configurationProperties.getName();
        this.anonymousBrowsingEnabled = configurationProperties.isAnonymousBrowsingEnabled();
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
