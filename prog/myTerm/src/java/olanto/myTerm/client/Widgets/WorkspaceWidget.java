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
import olanto.myTerm.client.Forms.TermForm;
import olanto.myTerm.client.Types.Concept;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.client.Types.LangSet;
import olanto.myTerm.client.Types.Term;

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
    private Concept c;
    private LangSet ls;
    private Tree staticTree = new Tree();
    private ConceptForm addcpt = new ConceptForm();
    private LangSetForm addterms = new LangSetForm();

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
                searchMenu.btnAdd.setEnabled(true);
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
//                createNewEntry(searchMenu.searchField.getText());
                searchMenu.btnAdd.setEnabled(false);
                getService().getAddResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), termCallback);

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
                if (event.getValue().contains("new")) {
                    createNewEntry(searchMenu.searchField.getText());
                } else {
                    getService().getdetailsForConcept(Long.parseLong(event.getValue()), conceptCallback);
                    getService().getdetailsForTerms(Long.parseLong(event.getValue()), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), termsCallback);
                }
            }
        });
        resultsPanel.adjustSize(0.25f, 0.3f);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void createNewEntry(String term) {
        Term t = new Term();
        t.form = term;
        ls.termList.add(t);
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        resultsPanel.conceptDetails.add(addcpt);
        addcpt.setVisible(true);
        addcpt.InitFromVariable(c);
        addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 70);
        resultsPanel.termsDetails.add(addterms);
        addterms.setVisible(true);
        addterms.adjustSize(addcpt.getOffsetWidth() - 5);
        addterms.initfromvar(ls);
        addterms.addTerm.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final TermForm ter = new TermForm();
                addterms.desc.add(ter);
                ter.adjustSize(addterms.getOffsetWidth() - 5);
                final Term t = new Term();
                t.form = "";
                t.language = "";
                ls.termList.add(t);
                ter.delete.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        ls.termList.remove(t);
                        addterms.desc.remove(ter);
                    }
                });
            }
        });
        addcpt.delete.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                ls.termList.clear();
                ls = null;
                c = null;
                addcpt.clearAllText();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                staticTree.remove(staticTree.getSelectedItem().getWidget());
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
