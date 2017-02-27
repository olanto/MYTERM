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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import olanto.myTerm.client.ContainerPanels.ResultsContainerREVISOR;
import olanto.myTerm.client.HeaderPanels.SearchHeaderREVISOR;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.Forms.ConceptFormREVISOR;
import olanto.myTerm.client.Forms.LangSetFormREVISOR;
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
public class REVISORWidget extends VerticalPanel {

    private static SearchHeaderREVISOR searchMenu;
    private static ResultsContainerREVISOR resultsPanel;
    private static AsyncCallback<String> entryApproveCallback;
    private static AsyncCallback<ConceptEntryDTO> entrySaveCallback;
    private static AsyncCallback<String> entryDisapproveCallback;
    private static AsyncCallback<String> workspaceCallback;
    private static AsyncCallback<ConceptEntryDTO> getConceptDetailsCallback;
    private static AsyncCallback<Boolean> printCallback;
    private static ConceptEntryDTO conceptEntryDTO;
    private static ConceptFormREVISOR addcpt;
    private static LangSetFormREVISOR addterms;
    private long ownerID;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    private ArrayList<String> lsList;
    private ArrayList<Long> rsList;
    public BooleanWrap isEdited = new BooleanWrap();
    private long conceptID = -1;

    public REVISORWidget(long idOwner, HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        ownerID = idOwner;
        sFields = sysFields;
        sysMsgs = sysMsg;
        rsList = new ArrayList<>();
        lsList = new ArrayList<>();
        fixGwtNav();
        searchMenu = new SearchHeaderREVISOR(ownerID, sysMsg);
        resultsPanel = new ResultsContainerREVISOR();
        add(searchMenu);
        add(resultsPanel);

        // Create an asynchronous callback to handle the result.
        entrySaveCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onSuccess(ConceptEntryDTO result) {
                if (result != null) {
                    conceptEntryDTO = result;
                    refreshContentFromConceptEntryDTO();
                    History.newItem("p2saved");
                } else {
                    History.newItem("p2notsaved");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                addcpt.save.setEnabled(true);
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.setWidget(new Label("Communication failed"));
                History.newItem("page2");
            }
        };
        entryApproveCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    History.newItem("p2approved");
                } else {
                    History.newItem("p2notapproved");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                addcpt.approve.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page2");
            }
        };
        entryDisapproveCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    History.newItem("p2disapproved");
                } else {
                    History.newItem("p2notdisapproved");
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page2");
            }
        };
        workspaceCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnSearch.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                } else {
                    resultsPanel.sideRes.setWidget(new HTML("No current entries"));
                }
                MainEntryPoint.statusPanel.setMessage("message", "Entries retrieved successfully...");
                History.newItem("p2loaded");
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnSearch.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page2");
            }
        };
        getConceptDetailsCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsDetails.setWidget(new Label("Communication failed"));
                History.newItem("page2");
                resultsPanel.printBtn.setVisible(false);
            }

            @Override
            public void onSuccess(ConceptEntryDTO result) {
                conceptEntryDTO = result;
                resultsPanel.printBtn.setVisible(true);
                refreshContentFromConceptEntryDTO();
                History.newItem("p2loaded");
            }
        };
        printCallback = new AsyncCallback<Boolean>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Issue generating XML file for print");
            }

            @Override
            public void onSuccess(Boolean result) {
                if (result) {
                    open(GWT.getHostPageBaseURL().replace("myTerm/", "") + "print/Concept" + conceptID + ".xml",
                            "_blank",
                            "menubar=no,"
                            + "location=false,"
                            + "resizable=yes,"
                            + "scrollbars=yes,"
                            + "status=no,"
                            + "dependent=true");
                }
            }
        };
        resultsPanel.printBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getService().printConceptEntry(searchMenu.rsrc.getItemText(searchMenu.rsrc.getSelectedIndex()), conceptID, searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), printCallback);
            }
        });
        searchMenu.btnSearch.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, "search").show();
                } else {
                    History.newItem("p2search");
                }
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, "search").show();
                    } else {
                        History.newItem("p2search");
                    }
                }
            }
        });
        searchMenu.langSrc.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                commandPage2();
            }
        });
        resultsPanel.adjustSize(0.2f, 0.3f);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("Appnew")) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, command).show();
                    } else {
                        long conceptID = Long.parseLong(command.substring(6));
                        getService().getRevisorDetailsForConcept(conceptID, ownerID, searchMenu.langSrc.getLangIDs(), getConceptDetailsCallback);
                    }
                } else {
                    switch (command) {
                        case "page2":
                            commandPage2();
                            break;
                        case "p2search":
                            commandSearch();
                            break;
                        case "p2saved":
                            commandSaved();
                            break;
                        case "p2notsaved":
                            commandNotSaved();
                            break;
                        case "p2approved":
                            commandApproved();
                            break;
                        case "p2notapproved":
                            commandNotApproved();
                            break;
                        case "p2disapproved":
                            commandDisapproved();
                            break;
                        case "p2notdisapproved":
                            commandNotDisapproved();
                            break;
                        case "p2loaded":
                            commandLoaded();
                            break;
                        case "p2escape":
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
            conceptID = conceptEntryDTO.concept.getIdConcept();
            addcpt = new ConceptFormREVISOR(searchMenu.rsrc, sFields, isEdited, sysMsgs);
            resultsPanel.conceptDetails.setWidget(addcpt);
            addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 5 * GuiConstant.WIDTH_UNIT);
            addcpt.setContentFromConceptEntryDTO(conceptEntryDTO.concept, isEdited, sysMsgs);
            if (!conceptEntryDTO.listlang.isEmpty()) {
                addterms = new LangSetFormREVISOR(ownerID, addcpt);
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
                        getService().RevisorUpdateConceptEntry(conceptEntryDTO, ownerID, searchMenu.langSrc.getLangIDs(), entrySaveCallback);
                    }
                }
            });
            addcpt.approve.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you sure that you want to approve this concept and all its associated terms?", 0, "").show();
                }
            });
            addcpt.disapprove.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you sure that you want to disapprove this concept and all its associated terms?", 1, "").show();
                }
            });
            addcpt.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, "p2escape").show();
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

    public void escapeEntry(String action) {
        isEdited.setVal(false);
        if (action.contains("Appnew")) {
            long conceptID = Long.parseLong(action.substring(6));
            getService().getRevisorDetailsForConcept(conceptID, ownerID, searchMenu.langSrc.getLangIDs(), getConceptDetailsCallback);
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
                    submit.setText(sysMsgs.get(GuiConstant.BTN_APPROVE));
                    break;
                case 1:
                    submit.setText(sysMsgs.get(GuiConstant.BTN_DISAPPROVE));
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
                            approveConceptEntry();
                            break;
                        case 1:
                            disapproveConceptEntry();
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
                    getService().RevisorSaveConceptEntry(conceptEntryDTO, ownerID, new AsyncCallback<String>() {
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
                            addcpt.save.setEnabled(true);
                            if (result != null) {
                                if (action.contains("Appnew")) {
                                    long conceptID = Long.parseLong(action.substring(6));
                                    getService().getRevisorDetailsForConcept(conceptID, ownerID, searchMenu.langSrc.getLangIDs(), getConceptDetailsCallback);
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

    private void commandPage2() {
        isEdited.setVal(false);
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        searchMenu.btnSearch.setEnabled(true);
        String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
        if ((lan == null) || (lan.isEmpty())) {
            lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
        }
        getService().getApproveElements("", lan, searchMenu.langSrc.getLangIDs(), searchMenu.rsrc.getResourcesIDs(), searchMenu.dom.getValue(searchMenu.dom.getSelectedIndex()), ownerID, workspaceCallback);
    }

    public void commandPageOther(String action) {
        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 2, action).show();
    }

    private void commandSearch() {
        isEdited.setVal(false);
        String lang = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
        String rsID = searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex());
        rsList.clear();
        lsList.clear();
        rsList.add(Long.parseLong(rsID));
        lsList.add(lang);
        resultsPanel.termsDetails.clear();
        resultsPanel.conceptDetails.clear();
        searchMenu.btnSearch.setEnabled(false);
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        getService().getApproveElements(searchMenu.searchField.getText().replace("*", "%"), lang, lsList, rsList, searchMenu.dom.getValue(searchMenu.dom.getSelectedIndex()), ownerID, workspaceCallback);
    }

    private void commandSaved() {
        addcpt.save.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry saved successfully");
        isEdited.setVal(false);
        History.newItem("page2");
    }

    private void commandNotSaved() {
        addcpt.save.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be updated");
    }

    private void commandApproved() {
        addcpt.approve.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry deleted successfully");
        isEdited.setVal(false);
        History.newItem("page2");
    }

    private void commandNotApproved() {
        addcpt.approve.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be deleted");
    }

    private void commandDisapproved() {
        addcpt.disapprove.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry submitted successfully");
        isEdited.setVal(false);
        History.newItem("page2");
    }

    private void commandNotDisapproved() {
        addcpt.disapprove.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be submitted");
    }

    private void approveConceptEntry() {
        getConceptEntryDTOFromWidget();
        getService().approveConceptEntry(conceptEntryDTO, ownerID, entryApproveCallback);
    }

    private void disapproveConceptEntry() {
        getConceptEntryDTOFromWidget();
        getService().disapproveConceptEntry(conceptEntryDTO, ownerID, entryDisapproveCallback);
    }

    private void commandLoaded() {
        isEdited.setVal(false);
        MainEntryPoint.statusPanel.setMessage("message", "Entry loaded successfully");
    }

    private void commandEscape() {
        searchMenu.btnSearch.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Changes cancelled successfully");
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        isEdited.setVal(false);
        History.newItem("p2escaped");
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;

    private static native void open(String url, String name, String features)/*-{
     var window = $wnd.open(url, name, features);
     return window;
     }-*/;
}
