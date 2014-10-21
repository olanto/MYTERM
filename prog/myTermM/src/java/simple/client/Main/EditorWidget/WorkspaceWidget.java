/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.client.Main.EditorWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import simple.client.Main.EditorWidget.Edit.EdConceptForm;
import simple.client.Main.EditorWidget.Edit.EdLangSetForm;
import simple.client.Main.Types.Concept;
import simple.client.Main.Types.Term;
import static simple.client.Main.publicWidget.MyTermSearchWidget.fixGwtNav;
import simple.client.Main.request.myTermService;
import simple.client.Main.request.myTermServiceAsync;

/**
 *
 * @author simple
 */
public class WorkspaceWidget extends VerticalPanel {

    private SearchHeaderWorkspace searchMenu = new SearchHeaderWorkspace();
    private SearchEditResultsContainer resultsPanel = new SearchEditResultsContainer();
    private static AsyncCallback<String> termCallback;
    private static AsyncCallback<String> conceptCallback;
    private VerticalPanel res = new VerticalPanel();
    private Concept c = new Concept();
    private Term t = new Term();
    private Tree staticTree = new Tree();
    private EdConceptForm addcpt = new EdConceptForm();
    private EdLangSetForm addterm = new EdLangSetForm();

    public WorkspaceWidget() {
        add(searchMenu);
        add(resultsPanel);

        fixGwtNav();
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultsPanel.resultsPan.setHeight(resultsPanel.termsPan.getOffsetHeight() + "px");
                resultsPanel.termsPan.add(new HTML(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.resultsPan.setHeight(resultsPanel.termsPan.getOffsetHeight() + "px");
                resultsPanel.termsPan.add(new Label("Communication failed"));
            }
        };
        conceptCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultsPanel.resultsPan.setHeight(resultsPanel.termsPan.getOffsetHeight() + "px");
                res.add(new HTML(result));
                resultsPanel.add(res);
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.resultsPan.setHeight(resultsPanel.termsPan.getOffsetHeight() + "px");
                res.add(new Label("Communication failed"));
                resultsPanel.add(res);
            }
        };
        // Listen for the button clicks
        searchMenu.btnSend.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Make remote call. Control flow will continue immediately and later
                // 'callback' will be invoked when the RPC completes.
                resultsPanel.termsPan.clear();
                resultsPanel.resultsPan.clear();
                resultsPanel.vptop.clear();
                res.clear();
                getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), termCallback);
            }
        });
        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                resultsPanel.termsPan.clear();
                resultsPanel.termsPan.add(staticTree);
                resultsPanel.resultsPan.clear();
                resultsPanel.vptop.clear();
                res.clear();
                createSourceTree(searchMenu.searchField.getText());
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    resultsPanel.termsPan.clear();
                    resultsPanel.resultsPan.clear();
                    resultsPanel.vptop.clear();
                    res.clear();
                    getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), termCallback);
                }
            }
        });

        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                res.clear();
                getService().getdetailsForConcept(Long.parseLong(event.getValue()), conceptCallback);

            }
        });
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void createSourceTree(String term) {
// Create the tree
        String language = searchMenu.langSrc.getItemText(searchMenu.langSrc.getSelectedIndex());
        staticTree.setStyleName("gwt-Tree");
        TreeItem docItem = new TreeItem();
        docItem.setStyleName("gwt-TreeItem");
        docItem.setTitle(term + ":" + language);
        docItem.setText(term + ":" + language);
        docItem.setHTML(term + ":" + language);
        staticTree.addItem(docItem);

        staticTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                int index = event.getSelectedItem().getText().indexOf(":");
                String term = event.getSelectedItem().getText().substring(0, index);
                String language = event.getSelectedItem().getText().substring(index + 1);
                t.form = term;
                t.language = language;
                c.subject_field = "Empty";
                resultsPanel.vptop.clear();
                resultsPanel.resultsPan.clear();
                resultsPanel.vptop.add(addcpt);
                addcpt.setVisible(true);
                addcpt.InitFromVariable(c);
                resultsPanel.resultsPan.add(addterm);
                addterm.setVisible(true);
                addterm.initfromvar(t);
                resultsPanel.resultsPan.setHeight((resultsPanel.termsPan.getOffsetHeight() - resultsPanel.vptop.getOffsetHeight()) + "px");

            }
        });
    }

    public void adjustSize(int height) {
        resultsPanel.adjustHeight(height - searchMenu.getOffsetHeight());
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
