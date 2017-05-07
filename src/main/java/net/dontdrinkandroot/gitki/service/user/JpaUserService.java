package net.dontdrinkandroot.gitki.service.user;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class JpaUserService implements UserService, ApplicationListener<ContextRefreshedEvent>
{
    @PersistenceContext
    private EntityManager entityManager;

    private PasswordEncoder passwordEncoder;

    protected JpaUserService()
    {
        /* RI */
    }

    public JpaUserService(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.where(builder.equal(from.get("email"), username));
        try {
            return this.entityManager.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    protected List<User> findAll()
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        query.from(User.class);

        return this.entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        List<User> users = this.findAll();
        if (users.isEmpty()) {
            User user = new User("Admin", "User", "admin@example.com", Role.ADMIN);
            user.setPassword(this.passwordEncoder.encode("admin"));
            this.entityManager.persist(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long findCount()
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> from = query.from(User.class);
        query.select(builder.count(from));

        return this.entityManager.createQuery(query).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> find(long first, long count)
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> from = query.from(User.class);

        TypedQuery<User> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setFirstResult((int) first);
        typedQuery.setMaxResults((int) count);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public User save(User user)
    {
        return this.entityManager.merge(user);
    }
}
