package net.dontdrinkandroot.gitki.wicket.page.user

import net.dontdrinkandroot.gitki.test.AbstractWicketTest
import org.junit.jupiter.api.Test

class UserEditPageTest : AbstractWicketTest() {

    @Test
    fun testAccess() {
        assertLoginRequired(UserEditPage::class.java)
        assertPageAccessible(UserEditPage::class.java, userWatcher)
        assertPageAccessible(UserEditPage::class.java, userCommitter)
        assertPageAccessible(UserEditPage::class.java, userAdmin)
    }
}