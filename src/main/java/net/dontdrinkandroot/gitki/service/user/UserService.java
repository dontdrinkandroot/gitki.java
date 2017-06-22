package net.dontdrinkandroot.gitki.service.user;

import net.dontdrinkandroot.gitki.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public interface UserService extends UserDetailsService
{
    long findCount();

    List<User> find(long first, long count);

    User save(User user);

    void setPassword(User user, String password);
}