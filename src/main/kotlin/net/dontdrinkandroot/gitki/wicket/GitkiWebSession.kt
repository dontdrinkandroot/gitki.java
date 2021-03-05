package net.dontdrinkandroot.gitki.wicket

import com.giffing.wicket.spring.boot.starter.configuration.extensions.external.spring.security.SecureWebSession
import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.service.configuration.ConfigurationService
import org.apache.wicket.Session
import org.apache.wicket.authorization.UnauthorizedInstantiationException
import org.apache.wicket.request.Request
import org.apache.wicket.request.component.IRequestableComponent
import org.apache.wicket.spring.injection.annot.SpringBean
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.time.ZoneId
import java.util.*

fun getGitkiSession() = Session.get() as GitkiWebSession

fun getCurrentUser() = getGitkiSession().user

class GitkiWebSession(request: Request?) : SecureWebSession(request) {

    @SpringBean
    private lateinit var configurationService: ConfigurationService

    val user: User?
        get() {
            if (!this.isSignedIn) return null
            val authentication = SecurityContextHolder.getContext().authentication
            val principal = authentication.principal
            if (null != principal && principal is User) return principal
            signOut()
            return null
        }

    /**
     * Manually sign in a specific user (useful for tests).
     *
     * @param user The user to sign in.
     */
    fun signIn(user: User) {
        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(user, null, user.authorities)
        this.signIn(true)
    }

    fun <T : IRequestableComponent?> assertAnonymousBrowsing(componentClass: Class<T>?) {
        if (!checkAnonymousBrowsing()) {
            throw UnauthorizedInstantiationException(componentClass)
        }
    }

    fun checkAnonymousBrowsing(): Boolean {
        if (configurationService.isAnonymousBrowsingEnabled) {
            return true
        }
        val user = user
        return null != user && user.role.containsRole(Role.WATCHER)
    }

    override fun getLocale(): Locale {
        val user = user
        return if (null != user) {
            user.locale
        } else super.getLocale()
    }

    val zoneId: ZoneId
        get() {
            val user = user
            return if (null != user) {
                ZoneId.of(user.zoneId)
            } else ZoneId.of("UTC")
        }

    fun hasRole(role: Role?): Boolean {
        val user = user
        return null != user && user.role.containsRole(role!!)
    }
}