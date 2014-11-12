/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Widgets;

import olanto.myTerm.client.ContainerPanels.SearchHeaderWorkspace;
import olanto.myTerm.client.ContainerPanels.ResultsContainer;
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
import olanto.myTerm.client.Forms.ConceptForm;
import olanto.myTerm.client.Forms.LangSetForm;
import olanto.myTerm.client.Types.Concept;
import olanto.myTerm.client.Types.Term;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 *
 * @author simple
 */
public class WorkspaceWidget extends VerticalPanel {
    
    private SearchHeaderWorkspace searchMenu;
    private ResultsContainer resultsPanel = new ResultsContainer();
    private static AsyncCallback<String> termCallback;
    private static AsyncCallback<String> conceptCallback;
    private static AsyncCallback<String> termsCallback;
    private Concept c = new Concept();
    private Term t = new Term();
    private Tree staticTree = new Tree();
    private ConceptForm addcpt = new ConceptForm();
    private LangSetForm addterm = new LangSetForm();
    
    public WorkspaceWidget(long ownerID) {
        fixGwtNav();
        searchMenu = new SearchHeaderWorkspace(ownerID);
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnSend.setEnabled(true);
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
                resultsPanel.termsPan.clear();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                searchMenu.btnSend.setEnabled(false);
                getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), termCallback);
            }
        });
        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                resultsPanel.termsPan.clear();
                resultsPanel.termsPan.add(staticTree);
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.clear();
                createSourceTree(searchMenu.searchField.getText());
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    resultsPanel.termsPan.clear();
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    searchMenu.btnSend.setEnabled(false);
                    getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), termCallback);
                }
            }
        });
        searchMenu.searchField.setFocus(true);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                getService().getdetailsForConcept(Long.parseLong(event.getValue()), conceptCallback);
                getService().getdetailsForTerms(Long.parseLong(event.getValue()), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), termsCallback);
                
            }
        });
        resultsPanel.adjustSize(0.25f, 0.3f);
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
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.add(addcpt);
                addcpt.setVisible(true);
                addcpt.InitFromVariable(c);
                addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 90);
                resultsPanel.termsDetails.add(addterm);
                addterm.setVisible(true);
                addterm.initfromvar(t);
                addterm.adjustSize(addcpt.getOffsetWidth() - 5);
            }
        });
    }
    
    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
