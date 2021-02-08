package net.dontdrinkandroot.gitki.wicket.page.user

import net.dontdrinkandroot.gitki.test.AbstractWicketTest
import net.dontdrinkandroot.gitki.wicket.page.admin.UserListPage
import org.junit.jupiter.api.Test

class UserListPageTest : AbstractWicketTest() {

    @Test
    fun testAccess() {
        assertLoginRequired(UserListPage::class.java)
        assertPageInaccessible(UserListPage::class.java, userWatcher)
        assertPageInaccessible(UserListPage::class.java, userCommitter)
        assertPageAccessible(UserListPage::class.java, userAdmin)
    }
}