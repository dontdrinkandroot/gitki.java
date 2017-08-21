package net.dontdrinkandroot.gitki.wicket.choicerenderer;

import net.dontdrinkandroot.gitki.model.AbstractPath;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class AbstractPathAbsoluteStringChoiceRenderer<T extends AbstractPath> implements IChoiceRenderer<T>
{
    @Override
    public Object getDisplayValue(T object)
    {
        return object.toAbsoluteString();
    }

    @Override
    public String getIdValue(T object, int index)
    {
        return object.toAbsoluteString();
    }

    @Override
    public T getObject(String id, IModel<? extends List<? extends T>> choices)
    {
        final List<? extends T> choicesObject = choices.getObject();
        for (int index = 0; index < choicesObject.size(); index++) {
            final T choice = choicesObject.get(index);
            if (this.getIdValue(choice, index).equals(id)) {
                return choice;
            }
        }

        return null;
    }
}
