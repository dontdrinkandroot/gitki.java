package net.dontdrinkandroot.gitki.service.user;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
@Service
public class JpaUserService implements UserService
{
    @PersistenceContext
    private EntityManager entityManager;

    protected JpaUserService()
    {
        /* RI */
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) throws UsernameNotFoundException
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
    @Transactional(readOnly = true)
    public boolean hasAdminUser()
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<User> from = query.from(User.class);
        query.select(builder.countDistinct(from.get("id")));
        query.where(builder.equal(from.get("role"), Role.ADMIN));

        TypedQuery<Long> typedQuery = this.entityManager.createQuery(query);

        Long numAdminUsers = typedQuery.getSingleResult();
        return numAdminUsers > 0;
    }

    @Override
    @Transactional
    public User save(User user, String password)
    {
        if (null != password) {
            user.setPassword(this.passwordEncoder().encode(password));
        }
        return this.entityManager.merge(user);
    }

    @Override
    @Transactional
    public void removeAll()
    {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> from = query.from(User.class);

        TypedQuery<User> typedQuery = this.entityManager.createQuery(query);
        List<User> users = typedQuery.getResultList();

        for (User user : users) {
            this.entityManager.remove(user);
        }
    }

    @Override
    @Transactional
    public void remove(User user)
    {
        User reloadedUser = this.entityManager.find(User.class, user.getId());
        this.entityManager.remove(reloadedUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
