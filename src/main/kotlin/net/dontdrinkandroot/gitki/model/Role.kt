package net.dontdrinkandroot.gitki.model

import org.springframework.security.core.GrantedAuthority
import java.util.*

enum class Role(val childRole: Role?) : GrantedAuthority {
    WATCHER(null),
    COMMITTER(WATCHER),
    ADMIN(COMMITTER);

    val rolesRecursive: Collection<Role>
        get() {
            val roles: MutableSet<Role> = HashSet()
            var currentRole: Role? = this
            while (currentRole != null) {
                roles.add(currentRole)
                currentRole = currentRole.childRole
            }
            return roles
        }

    fun containsRole(otherRole: Role): Boolean {
        var currentRole: Role? = this
        while (currentRole != null) {
            if (currentRole == otherRole) return true
            currentRole = currentRole.childRole
        }
        return false
    }

    override fun getAuthority() = name
}