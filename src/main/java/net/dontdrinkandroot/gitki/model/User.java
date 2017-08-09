package net.dontdrinkandroot.gitki.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Locale;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Entity
public class User implements UserDetails, GitUser
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
    private Role role = Role.WATCHER;

    @Column
    private String password;

    @Column(nullable = false)
    private String language = "en";

    @Column(nullable = false)
    private String zoneId = "UTC";

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

    public Locale getLocale()
    {
        return new Locale(this.language);
    }

    public String getLanguage()
    {
        return this.language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getZoneId()
    {
        return this.zoneId;
    }

    public void setZoneId(String zoneId)
    {
        this.zoneId = zoneId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        User user = (User) o;

        return this.id != null ? this.id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode()
    {
        return this.id != null ? this.id.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return this.getFullName() + "<" + this.getEmail() + ">";
    }
}
