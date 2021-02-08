package net.dontdrinkandroot.gitki.wicket.security

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import org.apache.wicket.Component
import org.apache.wicket.authorization.Action
import org.apache.wicket.authorization.IAuthorizationStrategy
import org.apache.wicket.request.component.IRequestableComponent
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.IResource
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class GitkiAuthorizationStrategy @Inject constructor(private val configurationService: ConfigurationService) :
    IAuthorizationStrategy {

    override fun <T : IRequestableComponent?> isInstantiationAuthorized(componentClass: Class<T>): Boolean {
        val annotation = componentClass.getAnnotation(Instantiate::class.java)
        return (null == annotation || annotation.allowAnonymousIfConfigured && isAnonymousBrowsingEnabled
                || getGitkiSession().hasRole(annotation.value))
    }

    override fun isActionAuthorized(component: Component, action: Action): Boolean {
        val componentClass: Class<out Component> = component.javaClass
        var requiredRole: Role? = null
        when (action.name) {
            Action.ENABLE -> {
                val enableAnnotation = componentClass.getAnnotation(Enable::class.java)
                if (null != enableAnnotation) {
                    requiredRole = enableAnnotation.value
                }
            }
            Action.RENDER -> {
                val renderAnnotation = componentClass.getAnnotation(Render::class.java)
                if (null != renderAnnotation) {
                    requiredRole = renderAnnotation.value
                }
            }
        }
        return null == requiredRole || getGitkiSession().hasRole(requiredRole)
    }

    override fun isResourceAuthorized(resource: IResource, parameters: PageParameters): Boolean {
        val annotation = resource.javaClass.getAnnotation(Instantiate::class.java)
        return (null == annotation || annotation.allowAnonymousIfConfigured && isAnonymousBrowsingEnabled
                || getGitkiSession().hasRole(annotation.value))
    }

    private val isAnonymousBrowsingEnabled: Boolean
        get() = configurationService.isAnonymousBrowsingEnabled
}