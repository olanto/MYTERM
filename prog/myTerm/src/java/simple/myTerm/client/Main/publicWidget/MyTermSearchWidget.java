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
package simple.myTerm.client.Main.publicWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import java.util.ArrayList;
import simple.myTerm.client.Main.request.myTermService;
import simple.myTerm.client.Main.request.myTermServiceAsync;

/**
 * Search interface class using the myTermService service.
 *
 * @author nizar ghoula - simple
 */
public class MyTermSearchWidget extends VerticalPanel {

    private SearchHeaderBasic searchMenu = new SearchHeaderBasic();
    private SearchResultsContainer resultsPanel = new SearchResultsContainer();
    private static AsyncCallback<String> termCallback;
    private static AsyncCallback<ArrayList<String>> termListCallback;

    public MyTermSearchWidget() {
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultsPanel.res.add(new HTML(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.res.add(new Label("Communication failed"));
            }
        };
        termListCallback = new AsyncCallback<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> result) {
                createSourceTree(result);
                getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getItemText(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getItemText(searchMenu.langTgt.getSelectedIndex()), termCallback);
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsPan.add(new Label("Communication failed"));
            }
        };
        // Listen for the button clicks
        searchMenu.btnSend.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                resultsPanel.termsPan.clear();
                resultsPanel.res.clear();
                getService().getResults(searchMenu.searchField.getText(), searchMenu.langSrc.getItemText(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getItemText(searchMenu.langTgt.getSelectedIndex()), termListCallback);
            }
        });
        // Listen for the button clicks
        searchMenu.inventory.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                resultsPanel.termsPan.clear();
                resultsPanel.res.clear();
                getService().getInventory(termCallback);
            }
        });
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    resultsPanel.termsPan.clear();
                    resultsPanel.res.clear();
                    getService().getResults(searchMenu.searchField.getText(), searchMenu.langSrc.getItemText(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getItemText(searchMenu.langTgt.getSelectedIndex()), termListCallback);
                }
            }
        });
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void createSourceTree(ArrayList<String> terms) {
// Create the tree
        Tree staticTree = new Tree();
        staticTree.setStyleName("gwt-Tree");
        for (int i = 0; i < terms.size(); i++) {
            String termdetails = terms.get(i);
            TreeItem docItem = new TreeItem();
            docItem.setStyleName("gwt-TreeItem");
            docItem.setTitle(termdetails);
            docItem.setText(termdetails);
            docItem.setHTML(termdetails);
            staticTree.addItem(docItem);
        }
        staticTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                if (event.getSelectedItem().getText() != null) {
                    resultsPanel.res.clear();
                    getService().getSearchResult(event.getSelectedItem().getText(), searchMenu.langSrc.getItemText(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getItemText(searchMenu.langTgt.getSelectedIndex()), termCallback);
                }
            }
        });
        resultsPanel.termsPan.add(staticTree);
    }
}
