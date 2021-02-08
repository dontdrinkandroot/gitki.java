package net.dontdrinkandroot.gitki.service.lock

import net.dontdrinkandroot.gitki.model.LockInfo

class LockedException(val lockInfo: LockInfo) : Exception()