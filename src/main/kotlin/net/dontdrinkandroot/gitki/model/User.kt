package net.dontdrinkandroot.gitki.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
class User : UserDetails, GitUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(nullable = false)
    var firstName: String? = null

    @Column(nullable = false)
    var lastName: String? = null

    @Column(nullable = false, unique = true)
    override var email: String = "user@example.com"

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role = Role.WATCHER

    @Column
    private var password: String? = null

    @Column(nullable = false)
    var language = "en"

    @Column(nullable = false)
    var zoneId = "UTC"

    constructor()

    constructor(firstName: String, lastName: String, email: String, role: Role) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.role = role
    }

    override val fullName: String
        get() = firstName + " " + lastName

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return role.rolesRecursive
    }

    override fun getPassword(): String {
        return password!!
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    val locale: Locale
        get() = Locale(language)

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || this.javaClass != o.javaClass) return false
        val user = o as User
        return if (id != null) id == user.id else user.id == null
    }

    override fun hashCode(): Int {
        return if (id != null) id.hashCode() else 0
    }

    override fun toString(): String {
        return fullName + "<" + email + ">"
    }
}