package net.dontdrinkandroot.gitki.config;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Component
@Validated
@ConfigurationProperties(prefix = GitkiConfigurationProperties.PROPERTY_PREFIX)
public class GitkiConfigurationProperties
{
    public static final String PROPERTY_PREFIX = "gitki";

    @NotEmpty
    private String name = "GitKi";

    @NotEmpty
    private String repository;

    private boolean anonymousBrowsingEnabled = true;

    public String getRepository()
    {
        return this.repository;
    }

    public void setRepository(String repository)
    {
        this.repository = repository;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isAnonymousBrowsingEnabled()
    {
        return this.anonymousBrowsingEnabled;
    }

    public void setAnonymousBrowsingEnabled(boolean anonymousBrowsingEnabled)
    {
        this.anonymousBrowsingEnabled = anonymousBrowsingEnabled;
    }
}
