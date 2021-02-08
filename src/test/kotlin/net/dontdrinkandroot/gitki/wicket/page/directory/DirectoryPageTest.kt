package net.dontdrinkandroot.gitki.wicket.page.directory

import net.dontdrinkandroot.gitki.test.AbstractWicketTest
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import org.junit.jupiter.api.Test

class DirectoryPageTest : AbstractWicketTest() {

    @Test
    fun testAccess() {
        assertPageAccessible(DirectoryPage::class.java, null)
        assertPageAccessible(DirectoryPage::class.java, userWatcher)
        assertPageAccessible(DirectoryPage::class.java, userCommitter)
        assertPageAccessible(DirectoryPage::class.java, userAdmin)
        configurationService.isAnonymousBrowsingEnabled = false
        getGitkiSession().invalidate()
        assertLoginRequired(DirectoryPage::class.java)
    }
}