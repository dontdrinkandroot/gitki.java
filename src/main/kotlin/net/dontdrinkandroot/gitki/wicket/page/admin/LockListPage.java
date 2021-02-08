package net.dontdrinkandroot.gitki.wicket.page.admin;

import net.dontdrinkandroot.gitki.model.LockInfo;
import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.service.lock.LockService;
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.ADMIN)
public class LockListPage extends DecoratorPage<List<LockInfo>>
{
    @SpringBean
    private LockService lockService;

    public LockListPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected IModel<String> createTitleModel() {
        return new StringResourceModel("gitki.locks");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.setModel(new ListModel<>(this.lockService.list()));
        this.add(new ListView<LockInfo>("locks", LockListPage.this.getModel())
        {
            @Override
            protected void populateItem(ListItem<LockInfo> item) {
                item.add(new Label("path", item.getModelObject().getPath()));
                item.add(new Label("user", item.getModelObject().getUser().getEmail()));
                item.add(new Label("expiry", item.getModelObject().getExpiry()));
            }
        });
    }
}
