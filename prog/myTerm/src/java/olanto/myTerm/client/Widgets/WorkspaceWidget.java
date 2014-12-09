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
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import olanto.myTerm.client.ContainerPanels.ResultsContainerWorkspace;
import olanto.myTerm.client.ContainerPanels.StatusPanel;
import olanto.myTerm.client.Forms.ConceptForm;
import olanto.myTerm.client.Forms.LangSetForm;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.LangEntryDTO;

/**
 *
 * @author simple
 */
public class WorkspaceWidget extends VerticalPanel {

    private static SearchHeaderWorkspace searchMenu;
    private static ResultsContainerWorkspace resultsPanel = new ResultsContainerWorkspace();
    private static AsyncCallback<String> termSearchCallback;
    private static AsyncCallback<String> termAddCallback;
    private static AsyncCallback<String> conceptCallback;
    private static AsyncCallback<String> termsCallback;
    private static AsyncCallback<ConceptEntryDTO> addTermsCallback;
    private static ConceptEntryDTO conceptEntryDTO;
    private static ConceptForm addcpt;
    private static ArrayList<LangSetForm> addterms;

    public WorkspaceWidget() {
        fixGwtNav();
        searchMenu = new SearchHeaderWorkspace();
        addterms = new ArrayList<>();
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termSearchCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnSend.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                } else {
                    StatusPanel.setMessage("warning", "Could not find what you are looking for, please try with a different term");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnSend.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        termAddCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    resultsPanel.addnewcpt.setVisible(true);
                } else {
                    new MyDialog("Concept not found would you like to create a new concept?", 0).show();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        conceptCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.conceptDetails.setWidget(new HTML(result));
                } else {
                    resultsPanel.conceptDetails.setWidget(new HTML("Concept details not found"));
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.conceptDetails.setWidget(new Label("Communication failed"));
            }
        };
        termsCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                resultsPanel.termsDetails.setWidget(new HTML(result));
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsDetails.setWidget(new Label("Communication failed"));
            }
        };
        addTermsCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsDetails.setWidget(new Label("Communication failed"));
            }

            @Override
            public void onSuccess(ConceptEntryDTO result) {
                conceptEntryDTO = result;
                refreshContentFromConceptEntryDTO();
            }
        };
        // Listen for the button clicks
        searchMenu.btnSend.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                StatusPanel.clearMessages();
                resultsPanel.addnewcpt.setVisible(false);
                resultsPanel.sideRes.clear();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                searchMenu.btnSend.setEnabled(false);
                getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), MainEntryPoint.userDTO.getId(), termSearchCallback);
            }
        });
        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                StatusPanel.clearMessages();
                resultsPanel.sideRes.clear();
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.clear();
                searchMenu.btnAdd.setEnabled(false);
                if ((searchMenu.searchField.getText().isEmpty()) || (searchMenu.searchField.getText().equals("%"))) {
                    Window.alert("Please indicate the term's form");
                    searchMenu.btnAdd.setEnabled(true);
                } else {
                    getService().getAddResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), MainEntryPoint.userDTO.getId(), termAddCallback);
                }
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    StatusPanel.clearMessages();
                    resultsPanel.addnewcpt.setVisible(false);
                    resultsPanel.sideRes.clear();
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    searchMenu.btnSend.setEnabled(false);
                    getService().getSearchResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), MainEntryPoint.userDTO.getId(), termSearchCallback);
                }
            }
        });
        searchMenu.searchField.setFocus(true);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                StatusPanel.clearMessages();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                if (event.getValue().contains("new")) {
                    long conceptID = Long.parseLong(event.getValue().substring(3));
                    getService().getAddDetailsForConcept(conceptID, MainEntryPoint.userDTO.getId(), addTermsCallback);
                } else {
                    getService().getdetailsForConcept(Long.parseLong(event.getValue()), MainEntryPoint.userDTO.getId(), conceptCallback);
                    getService().getdetailsForTerms(Long.parseLong(event.getValue()), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.langTgt.getValue(searchMenu.langTgt.getSelectedIndex()), MainEntryPoint.userDTO.getId(), termsCallback);
                }
            }
        });
        resultsPanel.addnewcpt.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                createNewEntry();
            }
        });
        resultsPanel.adjustSize(0.25f, 0.3f);
    }

    public void refreshContentFromConceptEntryDTO() {
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        if (conceptEntryDTO != null) {
            addcpt = new ConceptForm();
            addcpt.conceptDTO = conceptEntryDTO.concept;
            resultsPanel.conceptDetails.setWidget(addcpt);
            addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 70);
            addcpt.refreshContentFromConceptEntryDTO();
            addcpt.delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                }
            });
            if (!conceptEntryDTO.listlang.isEmpty()) {
                for (final LangEntryDTO langEntryDTO : conceptEntryDTO.listlang) {
                    final LangSetForm lset = new LangSetForm();
                    addterms.add(lset);
                    resultsPanel.termsDetails.setWidget(lset);
                    lset.refreshContentFromLangEntryDTO(langEntryDTO);
                    lset.adjustSize(addcpt.getOffsetWidth() - 5);
                }
            }
        } else {
            resultsPanel.termsDetails.setWidget(new Label("Concept details object is null, something is worng"));
        }
    }

    public static void createNewEntry() {
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        conceptEntryDTO = new ConceptEntryDTO();
        addcpt = new ConceptForm();
        addcpt.conceptDTO = conceptEntryDTO.concept;
        resultsPanel.conceptDetails.setWidget(addcpt);
        addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 70);
        LangSetForm lset = new LangSetForm();
        addterms.add(lset);
        resultsPanel.termsDetails.setWidget(lset);
        lset.adjustSize(addcpt.getOffsetWidth() - 5);
        addcpt.delete.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                conceptEntryDTO = null;
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
            }
        });
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private static class MyDialog extends DialogBox {

        public MyDialog(String text, int call) {
            // Set the dialog box's caption.
            setText(text);

            // Enable animation.
            setAnimationEnabled(true);

            // Enable glass background.
            setGlassEnabled(true);

            HorizontalPanel controls = new HorizontalPanel();
            // DialogBox is a SimplePanel, so you have to set its widget property to
            // whatever you want its contents to be.
            Button create = new Button("Create");
            create.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    createNewEntry();
                }
            });
            Button cancel = new Button("Cancel");
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                }
            });
            setPopupPosition(100, 100);
            setWidth("400px");
            controls.add(cancel);
            controls.add(create);
            setWidget(controls);
            controls.setWidth("400px");
            controls.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_LEFT);
            controls.setCellHorizontalAlignment(create, HorizontalPanel.ALIGN_RIGHT);
        }
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
