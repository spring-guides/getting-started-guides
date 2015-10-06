package hello;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.CssResourceReference;

import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.IeEdgeMetaTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.html.OptimizedMobileViewportMetaTag;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType;

public class BasePage extends WebPage implements IAjaxIndicatorAware {

    private static final String AJAX_INDICATOR_ID = "ajax-loader-mask";

    // tag::constructor[]
    public BasePage() {
        super();
        add(new HtmlTag("html"));
        add(new Label("title", new ResourceModel("page.title")));
        add(new OptimizedMobileViewportMetaTag("viewport"));
        add(new IeEdgeMetaTag("ie-edge"));
        add(newNavbar("navbar"));
        add(new Icon("ajax-indicator", FontAwesomeIconType.spinner).setMarkupId(AJAX_INDICATOR_ID));
    }
    // end::constructor[]

    protected Navbar newNavbar(String markupId) {
        Navbar navbar = new Navbar(markupId);
        navbar.setOutputMarkupId(true);
        navbar.setPosition(Navbar.Position.TOP);
        navbar.setInverted(true);
        navbar.setBrandName(new ResourceModel("app.name"));
        return navbar;
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return AJAX_INDICATOR_ID;
    }

    // tag::render-head[]
    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssReferenceHeaderItem.forReference(FontAwesomeCssReference.instance()));
        response.render(CssReferenceHeaderItem.forReference(new CssResourceReference(BasePage.class, "theme.css")));
    }
    // end::render-head[]

}