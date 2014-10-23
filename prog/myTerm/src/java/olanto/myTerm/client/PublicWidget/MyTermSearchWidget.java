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
package olanto.myTerm.client.PublicWidget;

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
import olanto.myTerm.client.myTermService;
import olanto.myTerm.client.myTermServiceAsync;

/**
 * Search interface class using the myTermService service.
 *
 * @author nizar ghoula - simple
 */
public class MyTermSearchWidget extends VerticalPanel {

    private SearchHeaderBasic searchMenu = new SearchHeaderBasic();
    private ResultsContainer resultsPanel = new ResultsContainer();
    private static AsyncCallback<String> termCallback;
    private static AsyncCallback<String> conceptCallback;

    public MyTermSearchWidget() {
        fixGwtNav();
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultsPanel.termsPan.add(new HTML(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsPan.add(new Label("Communication failed"));
            }
        };
        conceptCallback = new AsyncCallback<String>() {
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
                resultsPanel.termsPan.clear();
                resultsPanel.termsDetails.clear();
                getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), termCallback);
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    resultsPanel.termsPan.clear();
                    resultsPanel.termsDetails.clear();
                    getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), termCallback);
                }
            }
        });
        searchMenu.searchField.setFocus(true);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                resultsPanel.termsDetails.clear();
                getService().getdetailsForConcept(Long.parseLong(event.getValue()), conceptCallback);

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
