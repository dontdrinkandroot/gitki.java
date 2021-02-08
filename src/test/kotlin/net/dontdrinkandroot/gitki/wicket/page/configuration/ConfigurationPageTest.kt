package net.dontdrinkandroot.gitki.wicket.page.configuration

import net.dontdrinkandroot.gitki.test.AbstractWicketTest
import net.dontdrinkandroot.gitki.wicket.page.admin.ConfigurationPage
import org.junit.jupiter.api.Test

class ConfigurationPageTest : AbstractWicketTest() {

    @Test
    fun testAccess() {
        assertLoginRequired(ConfigurationPage::class.java)
        assertPageInaccessible(ConfigurationPage::class.java, userWatcher)
        assertPageInaccessible(ConfigurationPage::class.java, userCommitter)
        assertPageAccessible(ConfigurationPage::class.java, userAdmin)
    }
}