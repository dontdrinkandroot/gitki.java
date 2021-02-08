package net.dontdrinkandroot.gitki.wicket.security;

import net.dontdrinkandroot.gitki.model.Role;

import java.lang.annotation.*;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Documented
@Inherited
public @interface Instantiate
{
    Role value();

    boolean allowAnonymousIfConfigured() default false;
}
