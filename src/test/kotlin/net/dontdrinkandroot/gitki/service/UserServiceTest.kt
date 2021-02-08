package net.dontdrinkandroot.gitki.service

import net.dontdrinkandroot.gitki.model.User
import net.dontdrinkandroot.gitki.test.AbstractIntegrationTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserServiceTest : AbstractIntegrationTest() {

    @Test
    fun testHasAdminUser() {
        val user = userService.loadUserByUsername("admin@example.com") as User
        Assertions.assertTrue(userService.hasAdminUser())
        userService.remove(user)
        Assertions.assertFalse(userService.hasAdminUser())
    }
}