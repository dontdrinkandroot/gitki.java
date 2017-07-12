package net.dontdrinkandroot.gitki.config;

import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage;
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

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

    private Map<String, Class<? extends ViewPage>> viewMappings = new HashMap<>();

    private Map<String, Class<? extends EditPage>> editMappings = new HashMap<>();

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

    public Map<String, Class<? extends ViewPage>> getViewMappings()
    {
        return this.viewMappings;
    }

    public void setViewMappings(Map<String, Class<? extends ViewPage>> viewMappings)
    {
        this.viewMappings = viewMappings;
    }

    public Map<String, Class<? extends EditPage>> getEditMappings()
    {
        return this.editMappings;
    }

    public void setEditMappings(Map<String, Class<? extends EditPage>> editMappings)
    {
        this.editMappings = editMappings;
    }
}
