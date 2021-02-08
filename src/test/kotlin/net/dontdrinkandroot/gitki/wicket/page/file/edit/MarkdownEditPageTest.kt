package net.dontdrinkandroot.gitki.wicket.page.file.edit

import net.dontdrinkandroot.gitki.model.FilePath
import net.dontdrinkandroot.gitki.test.AbstractWicketTest
import net.dontdrinkandroot.gitki.wicket.getGitkiSession
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.eclipse.jgit.api.errors.GitAPIException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class MarkdownEditPageTest : AbstractWicketTest() {

    @BeforeEach
    @Throws(IOException::class, GitAPIException::class)
    fun initRepo() {
        logger.info("Initializing repository")
        gitService.addAndCommit(FilePath("test.md"), "Test\n====", userCommitter, "Adding test.md")
    }

    @Test
    fun testAccess() {
        assertLoginRequired(MarkdownEditPage::class.java, PageParameters().set(0, "test.md").set("action", "edit"))
        assertPageInaccessible(
            MarkdownEditPage::class.java,
            userWatcher, PageParameters().set(0, "test.md").set("action", "edit")
        )
        assertPageAccessible(
            MarkdownEditPage::class.java,
            userCommitter, PageParameters().set(0, "test.md").set("action", "edit")
        )
        lockService.clear()
        assertPageAccessible(
            MarkdownEditPage::class.java,
            userAdmin, PageParameters().set(0, "test.md").set("action", "edit")
        )
    }

    @Test
    fun testLocking() {
        getGitkiSession().signIn(userAdmin)
        wicketTester
            .startPage(MarkdownEditPage::class.java, PageParameters().set(0, "test.md").set("action", "edit"))
        getGitkiSession().signIn(userCommitter)
        wicketTester
            .startPage(MarkdownEditPage::class.java, PageParameters().set(0, "test.md").set("action", "edit"))
        Assertions.assertEquals(423, wicketTester.lastResponse.status)
    }
}