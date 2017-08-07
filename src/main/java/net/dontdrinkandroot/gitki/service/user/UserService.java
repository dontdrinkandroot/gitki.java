package net.dontdrinkandroot.gitki.service.user;

import net.dontdrinkandroot.gitki.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Iterator;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface UserService extends UserDetailsService
{
    long findCount();

    Iterator<User> find(long first, long count, String property, boolean ascending);

    boolean hasAdminUser();

    User save(User user, String password);

    void removeAll();

    void remove(User user);

    User find(Long id);
}
