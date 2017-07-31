package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class TemporalAccessorStringModel extends AbstractChainedReadonlyModel<TemporalAccessor, String>
{
    private final FormatStyle formatStyle;

    public TemporalAccessorStringModel(IModel<? extends TemporalAccessor> parent)
    {
        this(parent, FormatStyle.MEDIUM);
    }

    public TemporalAccessorStringModel(IModel<? extends TemporalAccessor> parent, FormatStyle formatStyle)
    {
        super(parent);
        this.formatStyle = formatStyle;
    }

    @Override
    public String getObject()
    {
        TemporalAccessor temporalAccessor = this.getParentObject();
        if (null == temporalAccessor) {
            return null;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime(this.formatStyle)
                        .withLocale(GitkiWebSession.get().getLocale())
                        .withZone(GitkiWebSession.get().getZoneId());

        return formatter.format(temporalAccessor);
    }
}
