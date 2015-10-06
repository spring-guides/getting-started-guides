package hello;

import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

import org.apache.wicket.Page;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapTheme;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.SingleThemeProvider;

public class HelloWebApplication extends WebApplication {

    // tag::home-page[]
    @Override
    public Class<? extends Page> getHomePage() {
        return ManageCustomersPage.class;
    }
    // end::home-page[]

    @Override
    protected void init() {
        super.init();

        // tag::routes[]
        mount(new MountedMapper("/", ManageCustomersPage.class));
        // end::routes[]

        // tag::spring-integration[]
        getComponentInstantiationListeners()
                .add(new SpringComponentInjector(this, getWebApplicationContext(getServletContext())));
        // end::spring-integration[]

        // tag::bootstrap-integration[]
        Bootstrap.install(this,
                new BootstrapSettings().setThemeProvider(new SingleThemeProvider(new BootstrapTheme())));
        // end::bootstrap-integration[]
    }

}
