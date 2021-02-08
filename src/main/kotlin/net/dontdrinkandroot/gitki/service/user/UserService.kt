package net.dontdrinkandroot.gitki.service.user

import net.dontdrinkandroot.gitki.model.User
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*

interface UserService : UserDetailsService {

    fun findCount(): Long
    fun find(first: Long, count: Long, property: String, ascending: Boolean): Iterator<User>
    fun hasAdminUser(): Boolean
    fun save(user: User, password: String?): User
    fun removeAll()
    fun remove(user: User)
    fun find(id: Long): Optional<User>
}