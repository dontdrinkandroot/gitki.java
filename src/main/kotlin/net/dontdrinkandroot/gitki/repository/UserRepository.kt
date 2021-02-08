package net.dontdrinkandroot.gitki.repository

import net.dontdrinkandroot.gitki.model.Role
import net.dontdrinkandroot.gitki.model.User
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : PagingAndSortingRepository<User, Long> {

    fun countByRole(role: Role): Long
    fun findByEmail(username: String): User
}