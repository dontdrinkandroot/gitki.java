package net.dontdrinkandroot.gitki.model;

import java.io.Serializable;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class User implements Serializable
{
    private String firstName;

    private String lastName;

    private String email;

    private Role role;

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
}
