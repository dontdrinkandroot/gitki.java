package net.dontdrinkandroot.gitki.service.user;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import net.dontdrinkandroot.gitki.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.Optional;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Service
public class RepositoryUserService implements UserService
{
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    protected RepositoryUserService()
    {
        /* RI */
    }

    @Inject
    public RepositoryUserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> find(Long id)
    {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = this.userRepository.findByEmail(username);
        if (null == user) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public long findCount()
    {
        return this.userRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterator<User> find(long first, long count, String property, boolean ascending)
    {
        PageRequest pageRequest = PageRequest.of(
                Math.toIntExact(first / count),
                Math.toIntExact(count),
                ascending ? Sort.Direction.ASC : Sort.Direction.DESC,
                property
        );
        return this.userRepository.findAll(pageRequest).iterator();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasAdminUser()
    {
        Long numAdminUsers = this.userRepository.countByRole(Role.ADMIN);
        return numAdminUsers > 0;
    }

    @Override
    @Transactional
    public User save(User user, String password)
    {
        if (null != password) {
            user.setPassword(this.passwordEncoder.encode(password));
        }
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeAll()
    {
        this.userRepository.deleteAll();
    }

    @Override
    @Transactional
    public void remove(User user)
    {
        this.userRepository.delete(user);
    }
}
