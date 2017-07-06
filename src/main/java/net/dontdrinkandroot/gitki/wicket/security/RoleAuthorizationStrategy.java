package net.dontdrinkandroot.gitki.wicket.security;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class RoleAuthorizationStrategy implements IAuthorizationStrategy
{
    @SpringBean
    private ConfigurationService configurationService;

    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> clazz)
    {
        Instantiate instantiateAnnotation = clazz.getAnnotation(Instantiate.class);
        return this.evalueInstantiateAnnotation(instantiateAnnotation);
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action)
    {
        if (Action.ENABLE.equals(action.getName())) {
            Enable enableAnnotation = component.getClass().getAnnotation(Enable.class);
            if (null != enableAnnotation) {
                return this.hasRole(enableAnnotation.value());
            }
        }

        if (Action.RENDER.equals(action.getName())) {
            Render renderAnnotation = component.getClass().getAnnotation(Render.class);
            if (null != renderAnnotation) {
                return this.hasRole(renderAnnotation.value());
            }
        }

        return true;
    }

    @Override
    public boolean isResourceAuthorized(IResource resource, PageParameters pageParameters)
    {
        Instantiate instantiateAnnotation = resource.getClass().getAnnotation(Instantiate.class);
        return this.evalueInstantiateAnnotation(instantiateAnnotation);
    }

    private boolean evalueInstantiateAnnotation(Instantiate instantiateAnnotation)
    {
        if (null != instantiateAnnotation) {

            if (instantiateAnnotation.anonymous() && this.configurationService.isAnonymousBrowsingEnabled()) {
                return true;
            }

            Role role = instantiateAnnotation.value();
            return this.hasRole(role);
        }

        return true;
    }

    private boolean hasRole(Role role)
    {
        User user = GitkiWebSession.get().getUser();
        return null != user && user.getRole().containsRole(role);
    }
}
