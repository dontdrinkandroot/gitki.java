package net.dontdrinkandroot.gitki.service.lock

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.model.GitUser
import net.dontdrinkandroot.gitki.model.SimpleGitUser
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Instant

class MapLockServiceTest {

    @Test
    fun testWorkflow() {
        val lockService: LockService = MapLockService()
        val user1: GitUser = SimpleGitUser("User1", "user1@example.com")
        val user2: GitUser = SimpleGitUser("User2", "user2@example.com")
        val path1 = FilePath("asdf")
        val path2 = FilePath("qwer")
        try {
            lockService.lock(path1, user1)
        } catch (e: LockedException) {
            Assertions.fail<Any>("Should not be locked")
        }
        val lockInfo1 = lockService.check(path1)
        val lockInfo1Expiry = lockInfo1!!.expiry
        Assertions.assertNotNull(lockInfo1)
        Assertions.assertEquals(user1, lockInfo1.user)
        Assertions.assertEquals(path1, lockInfo1.path)
        Assertions.assertNotNull(lockInfo1.expiry)
        try {
            try {
                Thread.sleep(10L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val lockInfo2 = lockService.lock(path1, user1)
            val newExpiry = lockInfo2.expiry
            Assertions.assertTrue(newExpiry.isAfter(lockInfo1Expiry))
        } catch (e: LockedException) {
            Assertions.fail<Any>("Should not be locked")
        }
        try {
            lockService.lock(path1, user2)
            Assertions.fail<Any>("Should be locked")
        } catch (e: LockedException) {
            /* Expected */
        }
        try {
            lockService.release(path1, user2)
            Assertions.fail<Any>("Should be locked")
        } catch (e: LockedException) {
            /* Expected */
        }
        try {
            lockService.release(path1, user1)
            Assertions.assertNull(lockService.check(path1))
        } catch (e: LockedException) {
            Assertions.fail<Any>("Should not be locked")
        }
        try {
            val lockInfo3 = lockService.lock(path2, user2)
            lockInfo3.expiry = Instant.now().minusSeconds(1)
            Assertions.assertNull(lockService.check(path2))
        } catch (e: LockedException) {
            Assertions.fail<Any>("Should not be locked")
        }
    }
}