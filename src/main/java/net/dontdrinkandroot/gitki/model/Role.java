package net.dontdrinkandroot.gitki.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public enum Role implements GrantedAuthority
{
    WATCHER(null),
    COMMITTER(WATCHER),
    ADMIN(COMMITTER);

    private final Role childRole;

    Role(Role childRole)
    {
        this.childRole = childRole;
    }

    public Role getChildRole()
    {
        return this.childRole;
    }

    public Collection<Role> getRolesRecursive()
    {
        Set<Role> roles = new HashSet<>();
        Role currentRole = this;
        while (currentRole != null) {
            roles.add(currentRole);
            currentRole = currentRole.getChildRole();
        }

        return roles;
    }

    public boolean containsRole(Role otherRole)
    {
        Role currentRole = this;
        while (currentRole != null) {
            if (currentRole.equals(otherRole)) {
                return true;
            }
            currentRole = currentRole.getChildRole();
        }

        return false;
    }

    @Override
    public final String getAuthority()
    {
        return this.name();
    }

    public static class Constants
    {
        public static final String WATCHER = "WATCHER";

        public static final String COMMITTER = "COMMITTER";

        public static final String ADMIN = "ADMIN";
    }
}
