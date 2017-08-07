package net.dontdrinkandroot.gitki.repository;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>
{
    Long countByRole(Role role);

    User findByEmail(String username);
}
