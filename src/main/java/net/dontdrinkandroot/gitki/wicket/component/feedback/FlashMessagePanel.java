package net.dontdrinkandroot.gitki.wicket.component.feedback;

import net.dontdrinkandroot.gitki.wicket.css.GitkiCssClass;
import net.dontdrinkandroot.wicket.behavior.CssClassAppender;
import net.dontdrinkandroot.wicket.bootstrap.component.feedback.FencedFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestHandler;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnLoadHeaderItem;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FlashMessagePanel extends FencedFeedbackPanel
{
    public FlashMessagePanel(String id)
    {
        super(id);
        this.setOutputMarkupId(true);
        this.add(new CssClassAppender(GitkiCssClass.FLASH_MESSAGES));
    }

    @Override
    public void onEvent(IEvent<?> event)
    {
        super.onEvent(event);

        if (this.anyMessage() && event.getPayload() instanceof AjaxRequestHandler) {
            AjaxRequestHandler ajaxRequestHandler = (AjaxRequestHandler) event.getPayload();
            ajaxRequestHandler.add(this);
            ajaxRequestHandler.appendJavaScript(this.getHideScript());
        }
    }

    @Override
    public void renderHead(IHeaderResponse response)
    {
        response.render(new OnLoadHeaderItem(this.getHideScript()));
    }

    protected String getHideScript()
    {
        return String.format(
                "$('#%s').delay(2000).slideUp(1000);",
                this.getMarkupId()
        );
    }
}
