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
import olanto.myTerm.client.ContainerPanels.SearchHeaderBasic;
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
import com.google.gwt.user.client.ui.HTML;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 * Search interface class using the myTermService service.
 *
 * @author nizar ghoula - simple
 */
public class READERWidget extends VerticalPanel {

    private SearchHeaderBasic searchMenu;
    private ResultsContainerBasic resultsPanel = new ResultsContainerBasic();
    private static AsyncCallback<String> termCallback;
    private static AsyncCallback<String> conceptCallback;
    private static AsyncCallback<String> termsCallback;
    private long ownerID;

    public READERWidget(long idOwner) {
        ownerID = idOwner;
        fixGwtNav();
        searchMenu = new SearchHeaderBasic(ownerID);
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnSend.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                } else {
                    MainEntryPoint.statusPanel.setMessage("warning", "Could not find what you are looking for, please try with a different term");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnSend.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
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
        // Listen for the button clicks
        searchMenu.btnSend.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.sideRes.clear();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                searchMenu.btnSend.setEnabled(false);
                getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termCallback);
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
                    getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termCallback);
                }
            }
        });
        searchMenu.searchField.setFocus(true);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                if (event.getValue().contains("TS")) {
                    long conceptID = Long.parseLong(event.getValue().substring(2));
                    getService().getdetailsForConcept(conceptID, ownerID, conceptCallback);
                    getService().getdetailsForTerms(conceptID, searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), ownerID, termsCallback);
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
}