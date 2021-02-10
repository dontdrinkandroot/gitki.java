package net.dontdrinkandroot.gitki.wicket.page.file.view;

import net.dontdrinkandroot.gitki.model.FilePath;
import net.dontdrinkandroot.gitki.wicket.util.PageParameterUtils;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.SharedResourceReference;

public class ImageViewPage extends ViewPage
{
    public ImageViewPage(PageParameters parameters) {
        super(parameters);
    }

    public ImageViewPage(IModel<FilePath> model) {
        super(model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        PageParameters resourceParameters = PageParameterUtils.from(this.getModelObject());
        this.add(new Image("image", new SharedResourceReference("raw"), resourceParameters)
        {
            @Override
            protected boolean shouldAddAntiCacheParameter() {
                return false;
            }
        });
    }
}
