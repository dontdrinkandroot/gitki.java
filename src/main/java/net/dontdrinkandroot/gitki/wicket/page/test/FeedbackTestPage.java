package net.dontdrinkandroot.gitki.wicket.page.test;

import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import net.dontdrinkandroot.wicket.bootstrap.component.button.AjaxButton;
import net.dontdrinkandroot.wicket.bootstrap.css.ButtonStyle;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FeedbackTestPage extends DecoratorPage<Void>
{
    @Override
    protected IModel<String> createTitleModel()
    {
        return Model.of("Feedback");
    }

    @Override
    protected void onInitialize()
    {
        super.onInitialize();

        this.getSession().info("This is an initial message");

        AjaxButton infoButton = new AjaxButton("info")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                FeedbackTestPage.this.getSession().info("This is an info message");
            }
        };
        infoButton.setButtonStyle(ButtonStyle.INFO);
        this.add(infoButton);

        AjaxButton successButton = new AjaxButton("success")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                FeedbackTestPage.this.getSession().success("This is a success message");
            }
        };
        successButton.setButtonStyle(ButtonStyle.SUCCESS);
        this.add(successButton);

        AjaxButton warnButton = new AjaxButton("warn")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                FeedbackTestPage.this.getSession().warn("This is a warning message");
            }
        };
        warnButton.setButtonStyle(ButtonStyle.WARNING);
        this.add(warnButton);

        AjaxButton errorButton = new AjaxButton("error")
        {
            @Override
            public void onClick(AjaxRequestTarget target)
            {
                FeedbackTestPage.this.getSession().error("This is an error message");
            }
        };
        errorButton.setButtonStyle(ButtonStyle.DANGER);
        this.add(errorButton);
    }
}
