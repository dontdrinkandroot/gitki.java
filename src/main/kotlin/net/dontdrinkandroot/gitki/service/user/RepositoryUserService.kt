package net.dontdrinkandroot.gitki.service.user

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.inject.Inject

@Service
class RepositoryUserService @Inject constructor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    @Transactional(readOnly = true)
    override fun find(id: Long): User? = userRepository.findByIdOrNull(id)

    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): User = userRepository.findByEmail(username)

    @Transactional(readOnly = true)
    override fun findCount(): Long = userRepository.count()

    @Transactional(readOnly = true)
    override fun find(first: Long, count: Long, property: String, ascending: Boolean): Iterator<User> {
        val pageRequest = PageRequest.of(
            Math.toIntExact(first / count),
            Math.toIntExact(count),
            if (ascending) Sort.Direction.ASC else Sort.Direction.DESC,
            property
        )
        return userRepository.findAll(pageRequest).iterator()
    }

    @Transactional(readOnly = true)
    override fun hasAdminUser(): Boolean = userRepository.countByRole(Role.ADMIN) > 0

    @Transactional
    override fun save(user: User, password: String?): User {
        if (null != password) user.setPassword(passwordEncoder.encode(password))
        return userRepository.save(user)
    }

    @Transactional
    override fun removeAll() = userRepository.deleteAll()

    @Transactional
    override fun remove(user: User) = userRepository.delete(user)
}