package net.dontdrinkandroot.gitki.wicket.choicerenderer;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;
import java.util.Locale;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class IsoLanguageChoiceRenderer implements IChoiceRenderer<String>
{
    @Override
    public Object getDisplayValue(String object)
    {
        Locale locale = new Locale(object);
        return locale.getDisplayLanguage(locale);
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
