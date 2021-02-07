package net.dontdrinkandroot.gitki.service.lock;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.model.GitUser;
import net.dontdrinkandroot.gitki.model.LockInfo;
import net.dontdrinkandroot.gitki.model.SimpleGitUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class MapLockServiceTest
{
    @Test
    public void testWorkflow()
    {
        LockService lockService = new MapLockService();

        GitUser user1 = new SimpleGitUser("User1", "user1@example.com");
        GitUser user2 = new SimpleGitUser("User2", "user2@example.com");

        FilePath path1 = new FilePath("asdf");
        FilePath path2 = new FilePath("qwer");

        try {
            lockService.lock(path1, user1);
        } catch (LockedException e) {
            Assertions.fail("Should not be locked");
        }

        LockInfo lockInfo1 = lockService.check(path1);
        Instant lockInfo1Expiry = lockInfo1.getExpiry();
        Assertions.assertNotNull(lockInfo1);
        Assertions.assertEquals(user1, lockInfo1.getUser());
        Assertions.assertEquals(path1, lockInfo1.getPath());
        Assertions.assertNotNull(lockInfo1.getExpiry());

        try {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockInfo lockInfo2 = lockService.lock(path1, user1);
            Instant newExpiry = lockInfo2.getExpiry();
            Assertions.assertTrue(newExpiry.isAfter(lockInfo1Expiry));
        } catch (LockedException e) {
            Assertions.fail("Should not be locked");
        }

        try {
            lockService.lock(path1, user2);
            Assertions.fail("Should be locked");
        } catch (LockedException e) {
            /* Expected */
        }

        try {
            lockService.release(path1, user2);
            Assertions.fail("Should be locked");
        } catch (LockedException e) {
            /* Expected */
        }

        try {
            lockService.release(path1, user1);
            Assertions.assertNull(lockService.check(path1));
        } catch (LockedException e) {
            Assertions.fail("Should not be locked");
        }

        try {
            LockInfo lockInfo3 = lockService.lock(path2, user2);
            lockInfo3.setExpiry(Instant.now().minusSeconds(1));
            Assertions.assertNull(lockService.check(path2));
        } catch (LockedException e) {
            Assertions.fail("Should not be locked");
        }
    }
}
