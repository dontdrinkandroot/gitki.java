package net.dontdrinkandroot.gitki.model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class User
{
    private String firstName;

    private String lastname;

    private String email;

    public String getFullName()
    {
        return this.firstName + " " + this.lastname;
    }

    public String getEmail()
    {
        return this.email;
    }
}
