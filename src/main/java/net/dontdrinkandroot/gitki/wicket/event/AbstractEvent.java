package net.dontdrinkandroot.gitki.wicket.event;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class AbstractEvent
{
    private AjaxRequestTarget target;

    public AbstractEvent()
    {
    }

    public AbstractEvent(AjaxRequestTarget target)
    {
        this.target = target;
    }

    public AjaxRequestTarget getTarget()
    {
        return this.target;
    }
}
