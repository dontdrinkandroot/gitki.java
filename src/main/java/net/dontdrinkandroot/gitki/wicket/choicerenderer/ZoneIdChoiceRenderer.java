package net.dontdrinkandroot.gitki.wicket.choicerenderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class ZoneIdChoiceRenderer implements IChoiceRenderer<String>
{
    @Override
    public Object getDisplayValue(String object)
    {
        ZoneId zoneId = ZoneId.of(object);
        return String.format("%s (%s)", zoneId.getId(), zoneId.getRules().getOffset(LocalDateTime.now()).getId());
    }

    @Override
    public String getIdValue(String object, int index)
    {
        return object;
    }

    @Override
    public String getObject(String id, IModel<? extends List<? extends String>> choices)
    {
        return id;
    }
}
