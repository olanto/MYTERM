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
import olanto.myTerm.client.ContainerPanels.ResultsContainerREVISOR;
import olanto.myTerm.client.ContainerPanels.SearchHeaderREVISOR;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.Forms.ConceptFormREVISOR;
import olanto.myTerm.client.Forms.LangSetFormREVISOR;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.LangEntryDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class REVISORWidget extends VerticalPanel {

    private static SearchHeaderREVISOR searchMenu;
    private static ResultsContainerREVISOR resultsPanel = new ResultsContainerREVISOR();
    private static AsyncCallback<String> entryApproveCallback;
    private static AsyncCallback<ConceptEntryDTO> entrySaveCallback;
    private static AsyncCallback<String> entryDisapproveCallback;
    private static AsyncCallback<String> workspaceCallback;
    private static AsyncCallback<ConceptEntryDTO> getConceptDetailsCallback;
    private static ConceptEntryDTO conceptEntryDTO;
    private static ConceptFormREVISOR addcpt;
    private static LangSetFormREVISOR addterms;
    private long ownerID;
    private HashMap<String, SysFieldDTO> sFields;
    public BooleanWrap isEdited = new BooleanWrap();

    public REVISORWidget(long idOwner, HashMap<String, SysFieldDTO> sysFields) {
        ownerID = idOwner;
        sFields = sysFields;
        fixGwtNav();
        searchMenu = new SearchHeaderREVISOR(ownerID);
        add(searchMenu);
        add(resultsPanel);

        // Create an asynchronous callback to handle the result.
        entrySaveCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onSuccess(ConceptEntryDTO result) {
                if (result != null) {
                    conceptEntryDTO = result;
//                Window.alert(conceptEntryDTO.toStringDTO());
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
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                } else {
                    resultsPanel.sideRes.setWidget(new HTML("No current entries"));
                }
                MainEntryPoint.statusPanel.setMessage("info", "Entries retrieved successfully");
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page2");
            }
        };
        getConceptDetailsCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsDetails.setWidget(new Label("Communication failed"));
                History.newItem("page2");
            }

            @Override
            public void onSuccess(ConceptEntryDTO result) {
                conceptEntryDTO = result;
//                Window.alert(conceptEntryDTO.toStringDTO());
                refreshContentFromConceptEntryDTO();
                History.newItem("loaded");
            }
        };

        searchMenu.btnSearch.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                MainEntryPoint.statusPanel.clearMessages();
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.clear();
                searchMenu.btnSearch.setEnabled(false);
                History.newItem("p2search");
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    MainEntryPoint.statusPanel.clearMessages();
                    resultsPanel.termsDetails.clear();
                    resultsPanel.conceptDetails.clear();
                    searchMenu.btnSearch.setEnabled(false);
                    History.newItem("p2search");
                }
            }
        });
        searchMenu.langSrc.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                commandPage2();
            }
        });
        resultsPanel.adjustSize(0.25f, 0.25f);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                if (event.getValue().contains("Appnew")) {
                    isEdited.setVal(false);
                    long conceptID = Long.parseLong(event.getValue().substring(6));
                    getService().getAddDetailsForConcept(conceptID, ownerID, getConceptDetailsCallback);
                } else {
                    String command = event.getValue();
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
                        case "loaded":
                            commandLoaded();
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
            addcpt = new ConceptFormREVISOR(searchMenu.rsrc, sFields, isEdited);
            resultsPanel.conceptDetails.setWidget(addcpt);
            addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 70);
            addcpt.setContentFromConceptEntryDTO(conceptEntryDTO.concept);
            if (!conceptEntryDTO.listlang.isEmpty()) {
                addterms = new LangSetFormREVISOR(ownerID, addcpt);
                addterms.adjustSize(addcpt.getOffsetWidth() - 20);
                resultsPanel.termsDetails.setWidget(addterms);
                for (LangEntryDTO langEntryDTO : conceptEntryDTO.listlang) {
                    addterms.refreshContentFromLangEntryDTO(langEntryDTO, searchMenu.langSrc.getLangIDs(), sFields, isEdited);
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
            addcpt.approve.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    addcpt.approve.setEnabled(false);
                    new MyDialog("Are you sure that you want to approve this concept and all its associated terms?", 0).show();
                }
            });
            addcpt.disapprove.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you sure that you want to disapprove this concept and all its associated terms?", 1).show();
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
        conceptEntryDTO.addTerm(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), 'e', ownerID);
    }

    public void escapeEntry() {
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        MainEntryPoint.statusPanel.setMessage("message", "Cancelled all modifications");
        History.newItem("p2escape");
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
                    submit.setText("Approve");
                    break;
                case 1:
                    submit.setText("Disapprove");
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
                            approveConceptEntry();
                            break;
                        case 1:
                            disapproveConceptEntry();
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
                    History.newItem("page2");
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

    private void commandPage2() {
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        searchMenu.btnSearch.setEnabled(true);
        String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
        if ((lan == null) || (lan.isEmpty())) {
            lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
        }
        getService().getApproveElementsShowByLang(lan, searchMenu.langSrc.getLangIDs(), searchMenu.rsrc.getResourcesIDs(), ownerID, workspaceCallback);
    }

    private void commandSearch() {
        resultsPanel.sideRes.clear();
        getService().getApproveResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, workspaceCallback);
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
        History.newItem("page2");
    }

    private void commandApproved() {
        addcpt.approve.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry deleted successfully");
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        History.newItem("page2");
    }

    private void commandNotApproved() {
        addcpt.approve.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be deleted");
        History.newItem("page2");
    }

    private void commandDisapproved() {
        addcpt.disapprove.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Entry submitted successfully");
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        History.newItem("page2");
    }

    private void commandNotDisapproved() {
        addcpt.disapprove.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("error", "Entry could not be submitted");
        History.newItem("page2");
    }

    private void approveConceptEntry() {
        getConceptEntryDTOFromWidget();
//                    Window.alert(conceptEntryDTO.toStringDTO());
        getService().approveConceptEntry(conceptEntryDTO, ownerID, entryApproveCallback);
    }

    private void disapproveConceptEntry() {
        getConceptEntryDTOFromWidget();
//                    Window.alert(conceptEntryDTO.toStringDTO());
        getService().disapproveConceptEntry(conceptEntryDTO, ownerID, entryDisapproveCallback);
    }

    private void commandLoaded() {
        MainEntryPoint.statusPanel.setMessage("message", "Entry loaded successfully");
        History.newItem("page2");
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
