package net.dontdrinkandroot.gitki.service.requestmapping;

import net.dontdrinkandroot.gitki.wicket.component.bspanel.index.IndexFilePanel;
import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage;
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
public class DefaultRequestMappingRegistry implements RequestMappingRegistry
{
    private Map<String, Class<? extends ViewPage>> viewMappings;

    private Map<String, Class<? extends EditPage>> editMappings;

    private Map<String, Class<? extends IndexFilePanel>> indexFileMappings;

    public DefaultRequestMappingRegistry()
    {
        this.viewMappings = new HashMap<>();
        this.editMappings = new HashMap<>();
        this.indexFileMappings = new HashMap<>();
    }

    @Inject
    public DefaultRequestMappingRegistry(
            @Value("#{gitkiConfigurationProperties.viewMappings}") Map<String, Class<? extends ViewPage>> viewMappings,
            @Value("#{gitkiConfigurationProperties.editMappings}") Map<String, Class<? extends EditPage>> editMappings,
            @Value("#{gitkiConfigurationProperties.indexFileMappings}") Map<String, Class<? extends IndexFilePanel>> indexFileMappings
    )
    {
        this.viewMappings = viewMappings;
        this.editMappings = editMappings;
        this.indexFileMappings = indexFileMappings;
    }

    @Override
    public void setViewMappings(Map<String, Class<? extends ViewPage>> mappings)
    {
        this.viewMappings = mappings;
    }

    @Override
    public void setEditMappings(Map<String, Class<? extends EditPage>> mappings)
    {
        this.editMappings = mappings;
    }

    @Override
    public void setIndexFileMappings(Map<String, Class<? extends IndexFilePanel>> mappings)
    {
        this.indexFileMappings = mappings;
    }

    @Override
    public void addViewMapping(String extension, Class<? extends ViewPage> pageClass)
    {
        this.viewMappings.put(extension, pageClass);
    }

    @Override
    public void addEditMapping(String extension, Class<? extends EditPage> pageClass)
    {
        this.editMappings.put(extension, pageClass);
    }

    @Override
    public Class<? extends ViewPage> resolveViewMapping(String extension)
    {
        if (!this.viewMappings.containsKey(extension)) {
            return null;
        }

        return this.viewMappings.get(extension);
    }

    @Override
    public Class<? extends EditPage> resolveEditMapping(String extension)
    {
        if (!this.editMappings.containsKey(extension)) {
            return null;
        }

        return this.editMappings.get(extension);
    }

    @Override
    public Class<? extends IndexFilePanel> resolveIndexFilePanel(String extension)
    {
        if (!this.indexFileMappings.containsKey(extension)) {
            return null;
        }

        return this.indexFileMappings.get(extension);
    }
}
