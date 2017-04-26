package net.dontdrinkandroot.gitki.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public enum Role
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
        Set<Role> roles = new HashSet<Role>();
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
}
