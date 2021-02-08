package net.dontdrinkandroot.gitki.model

data class SimpleGitUser(override val fullName: String, override val email: String) : GitUser {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.javaClass != other.javaClass) return false
        val that = other as SimpleGitUser
        return email == that.email
    }

    override fun hashCode(): Int = email.hashCode()
}