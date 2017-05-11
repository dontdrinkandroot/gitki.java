package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class InstantStringModel extends AbstractChainedReadonlyModel<Instant, String>
{
    DateTimeFormatter formatter;

    public InstantStringModel(IModel<? extends Instant> parent)
    {
        super(parent);
        this.formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.ENGLISH)
                .withZone(ZoneId.of("UTC"));
    }

    @Override
    public String getObject()
    {
        return this.formatter.format(this.getParentObject());
    }
}
