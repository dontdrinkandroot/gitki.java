package net.dontdrinkandroot.gitki.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Entity
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String password;

    public User()
    {
    }

    public User(String firstName, String lastName, String email, Role role)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public Long getId()
    {
        return this.id;
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

    public void setRole(Role role)
    {
        this.role = role;
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

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return this.lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
