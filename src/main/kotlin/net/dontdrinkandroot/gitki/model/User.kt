package net.dontdrinkandroot.gitki.model

import org.springframework.data.util.ProxyUtils
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
data class User(
    @NotBlank
    @Column(nullable = false)
    var firstName: String,

    @NotBlank
    @Column(nullable = false)
    var lastName: String,

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    override var email: String,

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.WATCHER,

    @Column
    private var password: String? = null,

    @NotNull
    @Column(nullable = false)
    var language: String = "en",

    @NotNull
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

    override fun equals(other: Any?): Boolean = when {
        null == other -> false
        this === other -> true
        javaClass != ProxyUtils.getUserClass(other) -> false
        else -> if (null == this.id) false else this.id == (other as User).id
    }

    override fun hashCode(): Int = 31

    override fun toString(): String = "$fullName<$email>"
}