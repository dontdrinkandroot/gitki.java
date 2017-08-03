package net.dontdrinkandroot.gitki.config;

import net.dontdrinkandroot.gitki.wicket.component.bspanel.index.IndexFilePanel;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage;
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private final Git git = new Git();

    private boolean anonymousBrowsingEnabled = true;

    private Map<String, Class<? extends ViewPage>> viewMappings = new HashMap<>();

    private Map<String, Class<? extends EditPage>> editMappings = new HashMap<>();

    private List<String> indexFiles = new ArrayList<>();

    private Map<String, Class<? extends IndexFilePanel>> indexFileMappings = new HashMap<>();

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

    public Map<String, Class<? extends IndexFilePanel>> getIndexFileMappings()
    {
        return this.indexFileMappings;
    }

    public void setIndexFileMappings(Map<String, Class<? extends IndexFilePanel>> indexFileMappings)
    {
        this.indexFileMappings = indexFileMappings;
    }

    public List<String> getIndexFiles()
    {
        return this.indexFiles;
    }

    public void setIndexFiles(List<String> indexFiles)
    {
        this.indexFiles = indexFiles;
    }

    public Git getGit()
    {
        return this.git;
    }

    public static class Git
    {
        @NotEmpty
        private String repository;

        private final Remote remote = new Remote();

        public String getRepository()
        {
            return this.repository;
        }

        public void setRepository(String repository)
        {
            this.repository = repository;
        }

        public Remote getRemote()
        {
            return this.remote;
        }

        public static class Remote
        {
            private final Autopush autopush = new Autopush();

            private final Autopull autopull = new Autopull();

            public Autopush getAutopush()
            {
                return this.autopush;
            }

            public Autopull getAutopull()
            {
                return this.autopull;
            }

            public static class Autopush
            {
                private boolean enabled = false;

                private Long interval = 300000L;

                public boolean isEnabled()
                {
                    return this.enabled;
                }

                public void setEnabled(boolean enabled)
                {
                    this.enabled = enabled;
                }

                public Long getInterval()
                {
                    return this.interval;
                }

                public void setInterval(Long interval)
                {
                    this.interval = interval;
                }
            }

            public static class Autopull
            {
                private boolean enabled = false;

                private Long interval = 300000L;

                public boolean isEnabled()
                {
                    return this.enabled;
                }

                public void setEnabled(boolean enabled)
                {
                    this.enabled = enabled;
                }

                public Long getInterval()
                {
                    return this.interval;
                }

                public void setInterval(Long interval)
                {
                    this.interval = interval;
                }
            }
        }
    }
}
