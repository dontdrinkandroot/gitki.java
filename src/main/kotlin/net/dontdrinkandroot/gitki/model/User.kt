package net.dontdrinkandroot.gitki.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
data class User(
    @Column(nullable = false)
    var firstName: String,
    @Column(nullable = false)
    var lastName: String,
    @Column(nullable = false, unique = true)
    override var email: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role,
    @Column
    private var password: String? = null,
    @Column(nullable = false)
    var language: String = "en",
    @Column(nullable = false)
    var zoneId: String = "UTC"
) : UserDetails, GitUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    override val fullName: String
        get() = "$firstName $lastName"

    override fun getAuthorities(): Collection<GrantedAuthority> = role.rolesRecursive

    override fun getPassword(): String? = password

    fun setPassword(password: String?) {
        this.password = password
    }

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    val locale: Locale
        get() = Locale(language)

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || this.javaClass != o.javaClass) return false
        val user = o as User
        return if (id != null) id == user.id else user.id == null
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String = "$fullName<$email>"
}