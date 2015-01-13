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

import olanto.myTerm.client.ContainerPanels.SearchHeaderREDACTOR;
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
import olanto.myTerm.client.ContainerPanels.ResultsContainerREDACTOR;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.Forms.ConceptFormREDACTOR;
import olanto.myTerm.client.Forms.LangSetFormREDACTOR;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.LangEntryDTO;

/**
 *
 * @author simple
 */
public class REDACTORWidget extends VerticalPanel {

    private static SearchHeaderREDACTOR searchMenu;
    private static ResultsContainerREDACTOR resultsPanel = new ResultsContainerREDACTOR();
    private static AsyncCallback<String> termAddCallback;
    private static AsyncCallback<String> termAddCallbackWS;
    private static AsyncCallback<String> termAddedCallback;
    private static AsyncCallback<String> entrySubmitCallback;
    private static AsyncCallback<String> entrySaveCallback;
    private static AsyncCallback<String> entryDeleteCallback;
    private static AsyncCallback<String> workspaceCallback;
    private static AsyncCallback<ConceptEntryDTO> getConceptDetailsCallback;
    private static ConceptEntryDTO conceptEntryDTO;
    private static ConceptFormREDACTOR addcpt;
    private static LangSetFormREDACTOR addterms;
    private long ownerID;

    public REDACTORWidget(long idOwner) {
        ownerID = idOwner;
        fixGwtNav();
        searchMenu = new SearchHeaderREDACTOR(ownerID);
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termAddCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    searchMenu.btnAdd.setEnabled(true);
                    resultsPanel.addnewcpt.setVisible(true);
                    MainEntryPoint.statusPanel.setMessage("message", "Found an existing entry of the same form");
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    History.newItem("found");
                } else {
                    searchMenu.btnAdd.setEnabled(true);
                    new MyDialog("Term not found would you like to create a new Entry?", 0).show();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        termAddCallbackWS = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
                if (result != null) {
                    searchMenu.btnAdd.setEnabled(true);
                    resultsPanel.addnewcpt.setVisible(true);
                    resultsPanel.sideRes.setWidget(new HTML(result));
                } else {
                    searchMenu.btnAdd.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        termAddedCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    History.newItem("added");
                } else {
                    History.newItem("notadded");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        // Create an asynchronous callback to handle the result.
        entrySaveCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    History.newItem("saved");
                } else {
                    History.newItem("notsaved");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                addcpt.save.setEnabled(true);
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.setWidget(new Label("Communication failed"));
            }
        };
        entrySubmitCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    History.newItem("submitted");
                } else {
                    History.newItem("notsubmitted");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                addcpt.submit.setEnabled(true);
                resultsPanel.sideCurrent.setWidget(new Label("Communication failed"));
            }
        };
        entryDeleteCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    History.newItem("deleted");
                } else {
                    History.newItem("notdeleted");
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
                MainEntryPoint.statusPanel.setMessage("Message", "Displaying current entries");
                if (result != null) {
                    resultsPanel.sideCurrent.setWidget(new HTML(result));
                } else {
                    resultsPanel.sideCurrent.setWidget(new HTML("No current entries"));
                }
            }

            @Override
            public void onFailure(Throwable caught) {
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
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.sideRes.clear();
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.clear();
                searchMenu.btnAdd.setEnabled(false);
                History.newItem("add");
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    MainEntryPoint.statusPanel.clearMessages();
                    resultsPanel.sideRes.clear();
                    resultsPanel.termsDetails.clear();
                    resultsPanel.conceptDetails.clear();
                    searchMenu.btnAdd.setEnabled(false);
                    History.newItem("add");
                }
            }
        });
        searchMenu.langSrc.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                commandPage1();
            }
        });
        resultsPanel.addnewcpt.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                createNewConceptEntry();
            }
        });
        resultsPanel.adjustSize(0.25f, 0.3f);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                if (event.getValue().contains("WSnew")) {
                    searchMenu.btnAdd.setEnabled(true);
                    long conceptID = Long.parseLong(event.getValue().substring(5));
                    getService().getAddDetailsForConcept(conceptID, ownerID, getConceptDetailsCallback);
                } else {
                    String command = event.getValue();
                    switch (command) {
                        case "page1":
                            commandPage1();
                            break;
                        case "add":
                            commandAdd();
                            break;
                        case "saved":
                            commandSaved();
                            break;
                        case "notsaved":
                            commandNotSaved();
                            break;
                        case "deleted":
                            commandDeleted();
                            break;
                        case "notdeleted":
                            commandNotDeleted();
                            break;
                        case "added":
                            commandAdded();
                            break;
                        case "notadded":
                            commandNotAdded();
                            break;
                        case "submitted":
                            commandSubmitted();
                            break;
                        case "notsubmitted":
                            commandNotSubmitted();
                            break;
                    }
                }
            }
        });
    }

    public void refreshContentFromConceptEntryDTO() {
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        if (conceptEntryDTO != null) {
            addcpt = new ConceptFormREDACTOR(searchMenu.rsrc);
            resultsPanel.conceptDetails.setWidget(addcpt);
            addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 70);
            addcpt.setContentFromConceptEntryDTO(conceptEntryDTO.concept);
            if (!conceptEntryDTO.listlang.isEmpty()) {
                addterms = new LangSetFormREDACTOR(ownerID);
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
                    getService().updateConceptEntry(conceptEntryDTO, ownerID, entrySaveCallback);
                }
            });
            addcpt.submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    addcpt.submit.setEnabled(false);
                    getConceptEntryDTOFromWidget();
//                    Window.alert(conceptEntryDTO.toStringDTO());
                    getService().submitConceptEntry(conceptEntryDTO, ownerID, entrySubmitCallback);
                }
            });
            addcpt.delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you sure that you want to remove this concept and all its associated terms?", 1).show();
                }
            });
            addcpt.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you sure that you want to cancel all the modifications?", 2).show();
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
        getService().createConceptEntry(conceptEntryDTO, ownerID, termAddedCallback);
    }

    public void deleteConceptEntry() {
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        getService().deleteConceptEntry(conceptEntryDTO.concept.getIdConcept(), ownerID, entryDeleteCallback);
    }

    public void escapeEntry() {
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        MainEntryPoint.statusPanel.setMessage("message", "Cancelled all modifications");
        History.newItem("escape");
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private static void getConceptEntryDTOFromWidget() {
        addcpt.setConceptDTOFromContent(conceptEntryDTO.concept);
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
            switch (call) {
                case 0:
                    submit.setText("Create");
                    break;
                case 1:
                    submit.setText("Delete");
                    break;
                case 2:
                    submit.setText("Escape modifications");
                    break;
            }
            submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    switch (call) {
                        case 0:
                            createNewConceptEntry();
                            break;
                        case 1:
                            deleteConceptEntry();
                            break;
                        case 2:
                            escapeEntry();
                            break;
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

    private void commandPage1() {
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        resultsPanel.sideRes.clear();
        searchMenu.btnAdd.setEnabled(true);
        String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
        if ((lan == null) || (lan.isEmpty())) {
            lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
        }
        getService().getWorkspaceElements(lan, ownerID, workspaceCallback);
        getService().getAddResult(searchMenu.searchField.getText(), lan, searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallbackWS);
    }

    private void commandAdd() {
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Adding a new entry entry, please wait...");
        if (searchMenu.searchField.getText().isEmpty()) {
            Window.alert("Please indicate the term's form");
            searchMenu.btnAdd.setEnabled(true);
            MainEntryPoint.statusPanel.setMessage("message", "No entries were added");
            History.newItem("notadded");
        } else {
            String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
            if ((lan == null) || (lan.isEmpty())) {
                lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
            }
            getService().getAddResult(searchMenu.searchField.getText(), lan, searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallback);
        }
    }

    private void commandSaved() {
        addcpt.save.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry saved successfully");
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
    }

    private void commandNotSaved() {
        addcpt.save.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be updated");
    }

    private void commandDeleted() {
        MainEntryPoint.statusPanel.setMessage("message", "Entry deleted successfully");
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        History.newItem("page1");
    }

    private void commandNotDeleted() {
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be deleted");
        History.newItem("page1");
    }

    private void commandAdded() {
        MainEntryPoint.statusPanel.setMessage("message", "Entry added successfully");
        History.newItem("page1");
    }

    private void commandNotAdded() {
        MainEntryPoint.statusPanel.setMessage("error", "No entry was added");
        History.newItem("page1");
    }

    private void commandSubmitted() {
        addcpt.submit.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry submitted successfully");
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        History.newItem("page1");
    }

    private void commandNotSubmitted() {
        addcpt.submit.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be submitted");
        History.newItem("page1");
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}