package net.dontdrinkandroot.gitki.service.requestmapping;

import net.dontdrinkandroot.gitki.wicket.page.file.edit.EditPage;
import net.dontdrinkandroot.gitki.wicket.page.file.view.ViewPage;

import java.util.Map;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class DefaultRequestMappingRegistry implements RequestMappingRegistry
{
    private Map<String, Class<? extends ViewPage>> viewMappings;

    private Map<String, Class<? extends EditPage>> editMappings;

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
}
