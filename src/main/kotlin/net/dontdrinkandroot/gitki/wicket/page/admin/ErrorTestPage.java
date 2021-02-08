package net.dontdrinkandroot.gitki.wicket.page.admin;

import net.dontdrinkandroot.gitki.model.Role;
import net.dontdrinkandroot.gitki.wicket.page.DecoratorPage;
import net.dontdrinkandroot.gitki.wicket.security.Instantiate;
import net.dontdrinkandroot.wicket.bootstrap.component.button.Button;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.http.flow.AbortWithHttpErrorCodeException;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Philip Washington Sorst <philip@sorst.net>
 */
@Instantiate(Role.ADMIN)
public class ErrorTestPage extends DecoratorPage
{
    public ErrorTestPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected IModel<String> createTitleModel() {
        return Model.of("Errors");
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        AbstractLink notFoundButton = new Button("notFound")
        {
            @Override
            public void onClick() {
                throw new AbortWithHttpErrorCodeException(404);
            }
        }.setBody(Model.of("Not Found"));
        this.add(notFoundButton);

        AbstractLink lockedButton = new Button("locked")
        {
            @Override
            public void onClick() {
                throw new AbortWithHttpErrorCodeException(423);
            }
        }.setBody(Model.of("Locked"));
        this.add(lockedButton);

        AbstractLink runtimeButton = new Button("runtime")
        {
            @Override
            public void onClick() {
                throw new RuntimeException("This is a runtime exception message");
            }
        }.setBody(Model.of("Runtime"));
        this.add(runtimeButton);
    }
}
