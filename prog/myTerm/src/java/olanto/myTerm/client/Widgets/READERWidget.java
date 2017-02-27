/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myTERM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myTERM. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package olanto.myTerm.client.Widgets;

import olanto.myTerm.client.ContainerPanels.ResultsContainerBasic;
import olanto.myTerm.client.HeaderPanels.SearchHeaderBasic;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import java.util.HashMap;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.GuiConstant;

/**
 * Search interface class using the myTermService service.
 *
 * @author nizar ghoula - simple
 */
public class READERWidget extends VerticalPanel {

    private SearchHeaderBasic searchMenu;
    private ResultsContainerBasic resultsPanel;
    private static AsyncCallback<String> termCallback;
    private static AsyncCallback<String> conceptCallback;
    private static AsyncCallback<String> termsCallback;
    private static AsyncCallback<Boolean> printCallback;
    private long ownerID;
    private long conceptID = -1;

    public READERWidget(long idOwner, HashMap<String, String> sysMsg) {
        ownerID = idOwner;
        fixGwtNav();
        searchMenu = new SearchHeaderBasic(ownerID, sysMsg);
        resultsPanel = new ResultsContainerBasic();
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Query processed successfully");
                searchMenu.btnSend.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                } else {
                    MainEntryPoint.statusPanel.setMessage("warning", "Could not find what you are looking for, please try with a different term");
                }
                History.newItem("page0");
            }

            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("error", "Problem processing the query...");
                searchMenu.btnSend.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page0");
            }
        };
        conceptCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.conceptDetails.add(new HTML(result));
                resultsPanel.printBtn.setVisible(true);
                getService().getdetailsForTerms(conceptID, searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), ownerID, GuiConstant.INTERFACE_LANG, termsCallback);
            }

            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.conceptDetails.add(new Label("Communication failed"));
                History.newItem("page0");
                resultsPanel.printBtn.setVisible(false);
            }
        };
        termsCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.termsDetails.add(new HTML(result));
                History.newItem("page0");
            }

            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.termsDetails.add(new Label("Communication failed"));
                History.newItem("page0");
            }
        };
        printCallback = new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Issue generating XML file for print");
            }

            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    open(Window.Location.getHost() + "/print/Concept" + conceptID + ".xml",
                            "_self",
                            "menubar=no,"
                            + "location=false,"
                            + "resizable=yes,"
                            + "scrollbars=yes,"
                            + "status=no,"
                            + "dependent=true");
                }
            }
        };

        // Listen for the button clicks
        searchMenu.btnSend.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.sideRes.clear();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                searchMenu.btnSend.setEnabled(false);
                MainEntryPoint.statusPanel.setMessage("warning", "Query Processing, Please Wait...");
                getService().getSearchResult(searchMenu.searchField.getText().replace("*", "%"), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getSelectedRsIDs(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getValue(searchMenu.dom.getSelectedIndex()), termCallback);
            }
        });
        resultsPanel.printBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getService().printConceptEntry(searchMenu.rsrc.getItemText(searchMenu.rsrc.getSelectedIndex()), conceptID, searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), printCallback);
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                MainEntryPoint.statusPanel.clearMessages();
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    resultsPanel.sideRes.clear();
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    searchMenu.btnSend.setEnabled(false);
                    MainEntryPoint.statusPanel.setMessage("warning", "Query Processing, Please Wait...");
                    getService().getSearchResult(searchMenu.searchField.getText().replace("*", "%"), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getSelectedRsIDs(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getValue(searchMenu.dom.getSelectedIndex()), termCallback);
                }
            }
        });
        searchMenu.searchField.setFocus(true);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                if (event.getValue().contains("TS")) {
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    conceptID = Long.parseLong(event.getValue().substring(2));
                    getService().getdetailsForConcept(conceptID, ownerID, conceptCallback);
                }
            }
        });
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

    private static native void open(String url, String name, String features)/*-{
     var window = $wnd.open(url, name, features);
     return window;
     }-*/;
}
