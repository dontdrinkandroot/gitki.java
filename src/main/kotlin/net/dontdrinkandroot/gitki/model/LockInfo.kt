package net.dontdrinkandroot.gitki.model

import java.io.Serializable
import java.time.Instant

class LockInfo(val path: FilePath, val user: GitUser, var expiry: Instant) : Serializable {

    val expired: Boolean
        get() = expiry.isBefore(Instant.now())
}