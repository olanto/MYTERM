/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client;

import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import java.util.ArrayList;

/**
 * Example class using the myTermService service.
 *
 * @author simple
 */
public class myTermSearch extends VerticalPanel {

    private Label termLabel = new Label("Input your search expression: ");
    private TextBox searchField = new TextBox();
    private TextBox langSrc = new TextBox();
    private TextBox langTgt = new TextBox();
    private Button btnSend = new Button("Search");
    private Button inventory = new Button("Inventory");
    private HorizontalPanel searchMenu = new HorizontalPanel();
    private HorizontalPanel resultsPanel = new HorizontalPanel();
    private ScrollPanel termsPan = new ScrollPanel();
    private ScrollPanel resultsPan = new ScrollPanel();
    private HTMLPanel res = new HTMLPanel("");
    private final AsyncCallback<String> termCallback;
    private final AsyncCallback<ArrayList<String>> termListCallback;

    public myTermSearch() {
        add(inventory);
        add(searchMenu);
        searchMenu.add(termLabel);
        searchMenu.add(new HTML("&nbsp;"));
        searchMenu.add(searchField);
        searchMenu.add(new HTML("&nbsp;"));
        searchMenu.add(new Label("Source Lang. "));
        searchMenu.add(langSrc);
        searchMenu.add(new HTML("&nbsp;"));
        searchMenu.add(new Label("Target Lang. "));
        searchMenu.add(langTgt);
        searchMenu.add(new HTML("&nbsp;"));
        searchMenu.add(btnSend);
        langSrc.setText("EN");
        langTgt.setText("FR");
        add(resultsPanel);
        resultsPanel.add(termsPan);
        resultsPanel.add(resultsPan);
        resultsPan.add(res);

        // Create an asynchronous callback to handle the result.
        termCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                res.clear();
                res.add(new HTML(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                res.clear();
                res.add(new Label("Communication failed"));
            }
        };
        termListCallback = new AsyncCallback<ArrayList<String>>() {
            @Override
            public void onSuccess(ArrayList<String> result) {
                termsPan.clear();
                res.clear();
                createSourceTree(result);
                getService().getSearchResult(searchField.getText(), langSrc.getText(), langTgt.getText(), termCallback);
            }

            @Override
            public void onFailure(Throwable caught) {
                termsPan.clear();
                res.clear();
                termsPan.add(new Label("Communication failed"));
            }
        };
        // Listen for the button clicks
        btnSend.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                getService().getResults(searchField.getText(), langSrc.getText(), langTgt.getText(), termListCallback);
            }
        });
        // Listen for the button clicks
        inventory.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                getService().getInventory(termCallback);
            }
        });
        searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    getService().getResults(searchField.getText(), langSrc.getText(), langTgt.getText(), termListCallback);
                }
            }
        });
    }

    public static myTermServiceAsync getService() {
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
                    getService().getSearchResult(event.getSelectedItem().getText(), langSrc.getText(), langTgt.getText(), termCallback);
                }
            }
        });
        termsPan.add(staticTree);
    }
}
