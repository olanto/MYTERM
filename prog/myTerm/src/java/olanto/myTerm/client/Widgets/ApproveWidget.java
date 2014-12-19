/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.client.ContainerPanels.ResultsContainerBasic;
import olanto.myTerm.client.ContainerPanels.SearchHeaderWorkspace;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 *
 * @author simple
 */
public class ApproveWidget extends VerticalPanel {

    private SearchHeaderWorkspace searchMenu;
    private ResultsContainerBasic resultsPanel = new ResultsContainerBasic();
    private static AsyncCallback<String> termCallback;
    private static AsyncCallback<String> conceptCallback;
    private static AsyncCallback<String> termsCallback;
    private long ownerID;

    public ApproveWidget(long idOwner) {
        ownerID = idOwner;
        fixGwtNav();
        searchMenu = new SearchHeaderWorkspace(ownerID);
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultsPanel.sideRes.add(new HTML(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.add(new Label("Communication failed"));
            }
        };
        conceptCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultsPanel.conceptDetails.add(new HTML(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.conceptDetails.add(new Label("Communication failed"));
            }
        };
        termsCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultsPanel.termsDetails.add(new HTML(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsDetails.add(new Label("Communication failed"));
            }
        };
       
        resultsPanel.adjustSize();
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
