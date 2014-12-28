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
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.math.BigInteger;
import java.util.Date;
import olanto.myTerm.client.ContainerPanels.ResultsContainerWorkspace;
import olanto.myTerm.client.ContainerPanels.StatusPanel;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.Forms.ConceptForm;
import olanto.myTerm.client.Forms.LangSetForm;
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
    private static AsyncCallback<String> termAddCallback;
    private static AsyncCallback<String> termSearchCallback;
    private static AsyncCallback<String> updateWSCallback;
    private static AsyncCallback<String> workspaceCallback;
    private static AsyncCallback<ConceptEntryDTO> getConceptDetailsCallback;
    private static ConceptEntryDTO conceptEntryDTO;
    private static ConceptForm addcpt;
    private static LangSetForm addterms;
    private long ownerID;

    public WorkspaceWidget(long idOwner) {
        ownerID = idOwner;
        fixGwtNav();
        searchMenu = new SearchHeaderWorkspace(ownerID);
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termAddCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.addnewcpt.setVisible(true);
                    getService().getWorkspaceElements(searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), ownerID, workspaceCallback);
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
        termSearchCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.clear();
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                } else {
                    resultsPanel.sideRes.clear();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        // Create an asynchronous callback to handle the result.
        updateWSCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    StatusPanel.setMessage("message", "Entry updated successfully");
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    History.newItem("page1");
                } else {
                    StatusPanel.setMessage("error", "Entry could not be updated");
                    resultsPanel.sideCurrent.clear();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideCurrent.setWidget(new Label("Communication failed"));
            }
        };
        workspaceCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    resultsPanel.sideCurrent.setWidget(new HTML(result));
                    getService().getAddResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termSearchCallback);
                } else {
                    searchMenu.btnAdd.setEnabled(true);
                    resultsPanel.sideCurrent.setWidget(new HTML("No current entries"));
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        getConceptDetailsCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsDetails.setWidget(new Label("Communication failed"));
            }

            @Override
            public void onSuccess(ConceptEntryDTO result) {
                conceptEntryDTO = result;
//                Window.alert(conceptEntryDTO.toStringDTO());
                refreshContentFromConceptEntryDTO();
            }
        };

        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                StatusPanel.clearMessages();
                resultsPanel.sideRes.clear();
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.clear();
                searchMenu.btnAdd.setEnabled(false);
                if (searchMenu.searchField.getText().isEmpty()) {
                    Window.alert("Please indicate the term's form");
                    searchMenu.btnAdd.setEnabled(true);
                } else {
                    getService().getAddResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallback);
                }
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    StatusPanel.clearMessages();
                    resultsPanel.sideRes.clear();
                    resultsPanel.termsDetails.clear();
                    resultsPanel.conceptDetails.clear();
                    searchMenu.btnAdd.setEnabled(false);
                    if (searchMenu.searchField.getText().isEmpty()) {
                        Window.alert("Please indicate the term's form");
                        searchMenu.btnAdd.setEnabled(true);
                    } else {
                        getService().getAddResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallback);
                    }
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
                    long conceptID = Long.parseLong(event.getValue().substring(3));
                    getService().getAddDetailsForConcept(conceptID, ownerID, getConceptDetailsCallback);
                } else if (event.getValue().contains("page1")) {
                    String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
                    if ((lan == null) || (lan.isEmpty())) {
                        lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
                    }
                    getService().getWorkspaceElements(lan, ownerID, workspaceCallback);
                }
            }
        });
        searchMenu.langSrc.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                getService().getWorkspaceElements(searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), ownerID, workspaceCallback);
            }
        });
        resultsPanel.addnewcpt.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                createNewConceptEntry();
            }
        });
        resultsPanel.adjustSize(0.25f, 0.3f);
    }

    public void refreshContentFromConceptEntryDTO() {
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        if (conceptEntryDTO != null) {
            addcpt = new ConceptForm(ownerID);
            resultsPanel.conceptDetails.setWidget(addcpt);
            addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 70);
            addcpt.refreshContentFromConceptEntryDTO(conceptEntryDTO.concept);
            if (!conceptEntryDTO.listlang.isEmpty()) {
                addterms = new LangSetForm(ownerID);
                addterms.adjustSize(addcpt.getOffsetWidth() - 20);
                resultsPanel.termsDetails.setWidget(addterms);
                for (LangEntryDTO langEntryDTO : conceptEntryDTO.listlang) {
                    addterms.refreshContentFromLangEntryDTO(langEntryDTO);
                }
            }
            addcpt.save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    addcpt.save.setEnabled(false);
                    getConceptEntryDTOFromWidget();
//                    Window.alert(conceptEntryDTO.toStringDTO());
                    getService().UpdateConceptEntry(conceptEntryDTO, ownerID, updateWSCallback);
                }
            });
            addcpt.submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    addcpt.save.setEnabled(false);
                    getConceptEntryDTOFromWidget();
//                    Window.alert(conceptEntryDTO.toStringDTO());
                    getService().submitConceptEntry(conceptEntryDTO, ownerID, termAddCallback);
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                }
            });
            addcpt.delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you sure that you want to remove this concept and all its associated terms?", 1).show();
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                }
            });
            addcpt.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
                    if ((lan == null) || (lan.isEmpty())) {
                        lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
                    }
                    getService().getWorkspaceElements(lan, ownerID, workspaceCallback);
                }
            });
        } else {
            resultsPanel.termsDetails.setWidget(new Label("Concept details object is null, something is worng"));
        }
    }

    public void getConceptEntryDTOFromHeader() {
        conceptEntryDTO = new ConceptEntryDTO();
        conceptEntryDTO.concept.setCreateBy(BigInteger.valueOf(ownerID));
        conceptEntryDTO.concept.setCreation(new Date(System.currentTimeMillis()));
        conceptEntryDTO.concept.setIdResource(searchMenu.rsrc.getIDResource(searchMenu.rsrc.getSelectedIndex()));
        conceptEntryDTO.concept.setLastmodified(new Date(System.currentTimeMillis()));
        conceptEntryDTO.concept.setLastmodifiedBy(BigInteger.valueOf(ownerID));
        conceptEntryDTO.concept.setSubjectField(searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()));
        conceptEntryDTO.addTerm(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), 'e');
    }

    public void createNewConceptEntry() {
        getConceptEntryDTOFromHeader();
//        Window.alert(conceptEntryDTO.toStringDTO());
        getService().createConceptEntry(conceptEntryDTO, ownerID, termAddCallback);
    }

    public void deleteConceptEntry() {
        getService().DeleteConceptEntry(conceptEntryDTO.concept.getIdConcept(), ownerID, updateWSCallback);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private static void getConceptEntryDTOFromWidget() {
        addcpt.updateConceptDTOFromContent(conceptEntryDTO.concept);
        addterms.sortTermDTOByLangSet(conceptEntryDTO.listlang);
    }

    private class MyDialog extends DialogBox {

        public MyDialog(String text, final int call) {
            // Set the dialog box's caption.
            setText(text);
            // Enable animation.
            setAnimationEnabled(true);
            // Enable glass background.
            setGlassEnabled(true);
            HorizontalPanel controls = new HorizontalPanel();
            // DialogBox is a SimplePanel, so you have to set its widget property to
            // whatever you want its contents to be.
            final Button submit = new Button("OK");
            if (call == 0) {
                submit.setText("Create");
            } else {
                submit.setText("Delete");
            }
            submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    if (call == 0) {
                        createNewConceptEntry();
                    } else {
                        deleteConceptEntry();
                    }
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
            controls.add(submit);
            setWidget(controls);
            controls.setWidth("400px");
            controls.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_LEFT);
            controls.setCellHorizontalAlignment(submit, HorizontalPanel.ALIGN_RIGHT);
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
