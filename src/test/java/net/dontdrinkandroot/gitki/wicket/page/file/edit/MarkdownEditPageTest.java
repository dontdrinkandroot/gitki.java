package net.dontdrinkandroot.gitki.wicket.page.file.edit;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.test.AbstractWicketTest;
import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class MarkdownEditPageTest extends AbstractWicketTest
{
    @BeforeEach
    public void initRepo() throws IOException, GitAPIException
    {
        this.logger.info("Initializing repository");
        this.gitService.addAndCommit(new FilePath("test.md"), "Test\n====", this.userCommitter, "Adding test.md");
    }

    @Test
    public void testAccess()
    {
        this.assertLoginRequired(MarkdownEditPage.class, new PageParameters().set(0, "test.md").set("action", "edit"));
        this.assertPageInaccessible(MarkdownEditPage.class,
                this.userWatcher, new PageParameters().set(0, "test.md").set("action", "edit")
        );
        this.assertPageAccessible(MarkdownEditPage.class,
                this.userCommitter, new PageParameters().set(0, "test.md").set("action", "edit")
        );
        this.lockService.clear();
        this.assertPageAccessible(MarkdownEditPage.class,
                this.userAdmin, new PageParameters().set(0, "test.md").set("action", "edit")
        );
    }

    @Test
    public void testLocking()
    {
        GitkiWebSession.get().signIn(this.userAdmin);
        this.getWicketTester()
                .startPage(MarkdownEditPage.class, new PageParameters().set(0, "test.md").set("action", "edit"));

        GitkiWebSession.get().signIn(this.userCommitter);
        this.getWicketTester()
                .startPage(MarkdownEditPage.class, new PageParameters().set(0, "test.md").set("action", "edit"));
        Assertions.assertEquals(423, this.getWicketTester().getLastResponse().getStatus());
    }
}
