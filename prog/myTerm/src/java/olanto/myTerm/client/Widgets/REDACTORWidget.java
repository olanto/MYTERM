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

import olanto.myTerm.client.HeaderPanels.SearchHeaderREDACTOR;
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
import java.util.HashMap;
import olanto.myTerm.client.ContainerPanels.ResultsContainerREDACTOR;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.Forms.ConceptFormREDACTOR;
import olanto.myTerm.client.Forms.LangSetFormREDACTOR;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.LangEntryDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class REDACTORWidget extends VerticalPanel {

    private static SearchHeaderREDACTOR searchMenu;
    private static ResultsContainerREDACTOR resultsPanel;
    private static AsyncCallback<String> termAddCallback;
    private static AsyncCallback<String> termAddCallbackWS;
    private static AsyncCallback<String> termAddedCallback;
    private static AsyncCallback<String> entrySubmitCallback;
    private static AsyncCallback<ConceptEntryDTO> entrySaveCallback;
    private static AsyncCallback<String> entryDeleteCallback;
    private static AsyncCallback<String> workspaceCallback;
    private static AsyncCallback<ConceptEntryDTO> getConceptDetailsCallback;
    private static ConceptEntryDTO conceptEntryDTO;
    private static ConceptFormREDACTOR addcpt;
    private static LangSetFormREDACTOR addterms;
    private long ownerID;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    public BooleanWrap isEdited = new BooleanWrap();

    public REDACTORWidget(long idOwner, HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        ownerID = idOwner;
        sFields = sysFields;
        sysMsgs = sysMsg;
        resultsPanel = new ResultsContainerREDACTOR(sysMsg);
        fixGwtNav();
        searchMenu = new SearchHeaderREDACTOR(ownerID, sysMsg);
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termAddCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.addnewcpt.setVisible(true);
                    MainEntryPoint.statusPanel.setMessage("message", "Found an existing entry of the same form");
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    History.newItem("found");
                } else {
                    new MyDialog("Term not found would you like to create a new Entry?", 0, "").show();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page1");
            }
        };
        termAddCallbackWS = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.addnewcpt.setVisible(true);
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    History.newItem("found");
                } else {
                    History.newItem("notfound");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page1");
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
                History.newItem("page1");
            }
        };
        // Create an asynchronous callback to handle the result.
        entrySaveCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onSuccess(ConceptEntryDTO result) {
                if (result != null) {
                    conceptEntryDTO = result;
//                Window.alert(conceptEntryDTO.toStringDTO());
                    refreshContentFromConceptEntryDTO();
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
                History.newItem("page1");
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
                History.newItem("page1");
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
                History.newItem("page1");
            }
        };
        workspaceCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    resultsPanel.sideCurrent.setWidget(new HTML(result));
                } else {
                    resultsPanel.sideCurrent.setWidget(new HTML("No current entries"));
                }
                MainEntryPoint.statusPanel.clearMessages();
                String srch = searchMenu.searchField.getText().replace("*", "%");
                String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
                if ((lan == null) || (lan.isEmpty())) {
                    lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
                }
                if ((srch != null) && (!srch.isEmpty())) {
                    getService().getAddResult(srch, lan, searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getValue(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallbackWS);
                }
                History.newItem("loaded");
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideCurrent.setWidget(new Label("Communication failed"));
                History.newItem("page1");
            }
        };
        getConceptDetailsCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsDetails.setWidget(new Label("Communication failed"));
                History.newItem("page1");
            }

            @Override
            public void onSuccess(ConceptEntryDTO result) {
                conceptEntryDTO = result;
//                Window.alert(conceptEntryDTO.toStringDTO());
                refreshContentFromConceptEntryDTO();
                History.newItem("loaded");
            }
        };

        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, "add").show();
                } else {
                    History.newItem("add");
                }
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, "add").show();
                    } else {
                        History.newItem("add");
                    }
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
        resultsPanel.adjustSize(0.2f, 0.3f);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("WSnew")) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, command).show();
                    } else {
                        long conceptID = Long.parseLong(command.substring(5));
                        getService().getRedactorDetailsForConcept(conceptID, ownerID, searchMenu.langSrc.getLangIDs(), getConceptDetailsCallback);
                    }
                } else {
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
                        case "loaded":
                            commandLoaded();
                            break;
                        case "escape":
                            commandEscape();
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
//            Window.alert(conceptEntryDTO.toStringDTO());
            addcpt = new ConceptFormREDACTOR(searchMenu.rsrc, sFields, isEdited, sysMsgs);
            resultsPanel.conceptDetails.setWidget(addcpt);
            addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 5 * GuiConstant.WIDTH_UNIT);
            addcpt.setContentFromConceptEntryDTO(conceptEntryDTO.concept, isEdited, sysMsgs);
            if (!conceptEntryDTO.listlang.isEmpty()) {
                addterms = new LangSetFormREDACTOR(ownerID, sFields, isEdited, sysMsgs);
                addterms.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - (int) 1.5 * GuiConstant.WIDTH_UNIT);
                resultsPanel.termsDetails.setWidget(addterms);
                for (LangEntryDTO langEntryDTO : conceptEntryDTO.listlang) {
                    addterms.refreshContentFromLangEntryDTO(langEntryDTO, searchMenu.langSrc.getLangIDs(), sFields, isEdited, sysMsgs);
                }
            }
            addcpt.save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        addcpt.save.setEnabled(false);
                        getConceptEntryDTOFromWidget();
//                        Window.alert(conceptEntryDTO.toStringDTO());
                        getService().RedactorUpdateConceptEntry(conceptEntryDTO, ownerID, searchMenu.langSrc.getLangIDs(), entrySaveCallback);
                    }
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
                    new MyDialog("Are you sure that you want to remove this concept and all its associated terms?", 1, "").show();
                }
            });
            addcpt.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, "escape").show();
                    } else {
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing has changed...");
                    }
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
        conceptEntryDTO.concept.setSubjectField(searchMenu.dom.getValue(searchMenu.dom.getSelectedIndex()));
        conceptEntryDTO.addTerm(searchMenu.searchField.getText().replace("%", ""), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), 'e', ownerID);
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

    public void escapeEntry(String action) {
        isEdited.setVal(false);
        if (action.contains("WSnew")) {
            long conceptID = Long.parseLong(action.substring(5));
            getService().getRedactorDetailsForConcept(conceptID, ownerID, searchMenu.langSrc.getLangIDs(), getConceptDetailsCallback);
        } else {
            History.newItem(action);
        }
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void getConceptEntryDTOFromWidget() {
        addcpt.setConceptDTOFromContent(conceptEntryDTO.concept);
        conceptEntryDTO.concept.setLastmodified(new Date(System.currentTimeMillis()));
        conceptEntryDTO.concept.setLastmodifiedBy(BigInteger.valueOf(ownerID));
        addterms.sortTermDTOByLangSet(conceptEntryDTO.listlang);
    }

    private class MyDialog extends DialogBox {

        public MyDialog(String text, final int call, final String action) {
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
                    submit.setText(sysMsgs.get(GuiConstant.BTN_CREATE));
                    break;
                case 1:
                    submit.setText(sysMsgs.get(GuiConstant.BTN_DELETE));
                    break;
                case 2:
                    submit.setText(sysMsgs.get(GuiConstant.BTN_ABORT));
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
                            escapeEntry(action);
                            break;
                    }
                }
            });
            Button cancel = new Button(sysMsgs.get(GuiConstant.BTN_CANCEL));
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    History.newItem("cancelled");
                }
            });
            Button save = new Button(sysMsgs.get(GuiConstant.BTN_SAVE));
            save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    addcpt.save.setEnabled(false);
                    getConceptEntryDTOFromWidget();
                    isEdited.setVal(false);
//                        Window.alert(conceptEntryDTO.toStringDTO());
                    getService().RedactorSaveConceptEntry(conceptEntryDTO, ownerID, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            addcpt.save.setEnabled(true);
                            resultsPanel.conceptDetails.clear();
                            resultsPanel.termsDetails.clear();
                            resultsPanel.conceptDetails.setWidget(new Label("Communication failed"));
                            History.newItem("notsaved");
                        }

                        @Override
                        public void onSuccess(String result) {
                            addcpt.save.setEnabled(false);
                            if (result != null) {
                                if (action.contains("WSnew")) {
                                    long conceptID = Long.parseLong(action.substring(5));
                                    getService().getRedactorDetailsForConcept(conceptID, ownerID, searchMenu.langSrc.getLangIDs(), getConceptDetailsCallback);
                                } else {
                                    History.newItem(action);
                                }
                            } else {
                                History.newItem("notsaved");
                            }
                        }
                    });
                }
            });
            setPopupPosition(100, 100);
            setWidth("400px");
            controls.add(cancel);
            controls.add(save);
            controls.add(submit);
            setWidget(controls);
            controls.setWidth("400px");
            controls.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_LEFT);
            controls.setCellHorizontalAlignment(save, HorizontalPanel.ALIGN_CENTER);
            controls.setCellHorizontalAlignment(submit, HorizontalPanel.ALIGN_RIGHT);
            if (call != 2) {
                save.setEnabled(false);
            }
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
    }

    public void commandPageOther(String action) {
        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, action).show();
    }

    private void commandAdd() {
        resultsPanel.termsDetails.clear();
        resultsPanel.conceptDetails.clear();
        searchMenu.btnAdd.setEnabled(false);
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Adding a new entry entry, please wait...");
        if (searchMenu.searchField.getText().isEmpty()) {
            Window.alert("Please indicate the term's form");
            MainEntryPoint.statusPanel.setMessage("message", "No entries were added");
            History.newItem("notadded");
        } else {
            String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
            if ((lan == null) || (lan.isEmpty())) {
                lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
            }
            getService().getAddResult(searchMenu.searchField.getText().replace("*", "%"), lan, searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getValue(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallback);
        }
    }

    private void commandSaved() {
        addcpt.save.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry saved successfully");
        isEdited.setVal(false);
        History.newItem("page1");
    }

    private void commandNotSaved() {
        addcpt.save.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be updated");
    }

    private void commandDeleted() {
        addcpt.delete.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry deleted successfully");
        isEdited.setVal(false);
        History.newItem("page1");
    }

    private void commandNotDeleted() {
        addcpt.delete.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be deleted");
    }

    private void commandAdded() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry added successfully");
        isEdited.setVal(false);
        History.newItem("page1");
    }

    private void commandNotAdded() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "No entry was added");
        isEdited.setVal(false);
        History.newItem("page1");
    }

    private void commandLoaded() {
        MainEntryPoint.statusPanel.setMessage("message", "Entry loaded successfully");
        isEdited.setVal(false);
    }

    private void commandSubmitted() {
        addcpt.submit.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry submitted successfully");
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        isEdited.setVal(false);
        History.newItem("page1");
    }

    private void commandEscape() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Changes cancelled successfully");
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        isEdited.setVal(false);
        History.newItem("escaped");
    }

    private void commandNotSubmitted() {
        addcpt.submit.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be submitted");
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
