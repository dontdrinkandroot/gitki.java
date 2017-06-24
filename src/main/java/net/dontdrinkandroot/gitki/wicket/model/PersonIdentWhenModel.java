package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;
import org.eclipse.jgit.lib.PersonIdent;

import java.time.ZonedDateTime;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class PersonIdentWhenModel extends AbstractChainedReadonlyModel<PersonIdent, ZonedDateTime>
{
    public PersonIdentWhenModel(IModel<? extends PersonIdent> parent)
    {
        super(parent);
    }

    @Override
    public ZonedDateTime getObject()
    {
        PersonIdent personIdent = this.getParentObject();
        return ZonedDateTime.ofInstant(personIdent.getWhen().toInstant(), personIdent.getTimeZone().toZoneId());
    }
}
