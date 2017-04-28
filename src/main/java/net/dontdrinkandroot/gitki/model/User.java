package net.dontdrinkandroot.gitki.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class User implements UserDetails
{
    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private String password;

    protected User()
    {
        /* RI */
    }

    public User(String firstName, String lastName, String email, Role role)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public String getFullName()
    {
        return this.firstName + " " + this.lastName;
    }

    public String getEmail()
    {
        return this.email;
    }

    public Role getRole()
    {
        return this.role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.role.getRolesRecursive();
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String getUsername()
    {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
