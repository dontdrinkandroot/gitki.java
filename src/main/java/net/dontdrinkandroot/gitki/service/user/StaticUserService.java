package net.dontdrinkandroot.gitki.service.user;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class StaticUserService implements UserService
{
    private Map<String, User> userMap = new HashMap<>();

    private PasswordEncoder passwordEncoder;

    protected StaticUserService()
    {
        /* RI */
    }

    public StaticUserService(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
        this.addUser("Admin", "User", "admin@example.com", Role.ADMIN);
        this.addUser("Committer", "User", "committer@example.com", Role.COMMITTER);
        this.addUser("Watcher", "User", "watcher@example.com", Role.WATCHER);
    }

    private void addUser(String firstName, String lastName, String eMail, Role role)
    {
        User user = new User(firstName, lastName, eMail, role);
        user.setPassword(this.passwordEncoder.encode(role.name().toLowerCase()));
        this.userMap.put(eMail, user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if (!this.userMap.containsKey(username)) {
            throw new UsernameNotFoundException("No user found with the given name");
        }

        return this.userMap.get(username);
    }

    @Override
    public long findCount()
    {
        return this.userMap.size();
    }

    @Override
    public List<User> find(long first, long count)
    {
        List<User> users = new ArrayList<>(this.userMap.values());
        int size = users.size();
        return users.subList((int) Math.min(size - 1, first), (int) Math.min(size - 1, first + count));
    }

    @Override
    public User save(User user)
    {
        throw new UnsupportedOperationException();
    }
}
