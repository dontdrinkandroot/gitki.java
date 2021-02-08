package net.dontdrinkandroot.gitki.wicket.model;

import net.dontdrinkandroot.wicket.model.AbstractChainedReadonlyModel;
import org.apache.wicket.model.IModel;

import java.text.DecimalFormat;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
public class FileSizeStringModel extends AbstractChainedReadonlyModel<Long, String>
{
    public FileSizeStringModel(IModel<? extends Long> parent) {
        super(parent);
    }

    @Override
    public String getObject() {
        Long size = this.getParentObject();

        if (size <= 0)
            return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("###0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
