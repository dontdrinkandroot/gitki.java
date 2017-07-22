package net.dontdrinkandroot.gitki.model;

import java.util.Objects;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class SimpleGitUser implements GitUser
{
    private String email;

    private String fullname;

    public SimpleGitUser(String fullname, String email)
    {
        Objects.requireNonNull(fullname);
        Objects.requireNonNull(email);
        this.email = email;
        this.fullname = fullname;
    }

    @Override
    public String getFullName()
    {
        return null;
    }

    @Override
    public String getEmail()
    {
        return this.email;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;

        SimpleGitUser that = (SimpleGitUser) o;

        return this.email.equals(that.email);
    }

    @Override
    public int hashCode()
    {
        return this.email.hashCode();
    }
}
