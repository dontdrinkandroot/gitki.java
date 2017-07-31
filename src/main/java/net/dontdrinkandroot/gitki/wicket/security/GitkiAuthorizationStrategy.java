package net.dontdrinkandroot.gitki.wicket.security;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
public class GitkiAuthorizationStrategy implements IAuthorizationStrategy
{
    private ConfigurationService configurationService;

    protected GitkiAuthorizationStrategy()
    {
        /* Reflection Instantiation */
    }

    @Inject
    public GitkiAuthorizationStrategy(ConfigurationService configurationService)
    {
        this.configurationService = configurationService;
    }

    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass)
    {
        Instantiate annotation = componentClass.getAnnotation(Instantiate.class);
        return
                null == annotation
                        || (annotation.allowAnonymousIfConfigured() && this.isAnonymousBrowsingEnabled())
                        || GitkiWebSession.get().hasRole(annotation.value());
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action)
    {
        Class<? extends Component> componentClass = component.getClass();
        Role requiredRole = null;
        switch (action.getName()) {
            case Action.ENABLE:
                Enable enableAnnotation = componentClass.getAnnotation(Enable.class);
                if (null != enableAnnotation) {
                    requiredRole = enableAnnotation.value();
                }
                break;
            case Action.RENDER:
                Render renderAnnotation = componentClass.getAnnotation(Render.class);
                if (null != renderAnnotation) {
                    requiredRole = renderAnnotation.value();
                }
                break;
        }

        return null == requiredRole || GitkiWebSession.get().hasRole(requiredRole);
    }

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters parameters)
    {
        Instantiate annotation = resource.getClass().getAnnotation(Instantiate.class);
        return
                null == annotation
                        || (annotation.allowAnonymousIfConfigured() && this.isAnonymousBrowsingEnabled())
                        || GitkiWebSession.get().hasRole(annotation.value());
    }

    private boolean isAnonymousBrowsingEnabled()
    {
        return this.configurationService.isAnonymousBrowsingEnabled();
    }
}
