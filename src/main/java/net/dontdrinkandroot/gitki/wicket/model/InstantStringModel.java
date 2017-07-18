package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.gitki.wicket.GitkiWebSession;
import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class InstantStringModel extends AbstractChainedReadonlyModel<Instant, String>
{
    private final FormatStyle formatStyle;

    public InstantStringModel(IModel<? extends Instant> parent)
    {
        this(parent, FormatStyle.MEDIUM);
    }

    public InstantStringModel(IModel<? extends Instant> parent, FormatStyle formatStyle)
    {
        super(parent);
        this.formatStyle = formatStyle;
    }

    @Override
    public String getObject()
    {
        Instant instant = this.getParentObject();
        if (null == instant) {
            return null;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime(this.formatStyle)
                        .withLocale(GitkiWebSession.get().getLocale())
                        .withZone(ZoneId.of("UTC"));

        return formatter.format(instant);
    }
}
