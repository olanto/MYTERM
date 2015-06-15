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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import olanto.myTerm.client.ContainerPanels.ResultsContainerADMIN;
import olanto.myTerm.client.ContainerPanels.SearchHeaderDOMAIN;
import olanto.myTerm.client.Forms.DomainFormADMIN;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.DomainDTO;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class DOMAINSWidget extends VerticalPanel {

    private static SearchHeaderDOMAIN searchMenu;
    private static ResultsContainerADMIN resultsPanel;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    private static AsyncCallback<String> domainsCallback;
    private static AsyncCallback<String> domainAddCallback;
    private static AsyncCallback<String> domainAddedCallback;
    private static AsyncCallback<DomainDTO> getDomainDetailsSaveCallback;
    private static AsyncCallback<DomainDTO> getDomainDetailsCallback;
    public BooleanWrap isEdited = new BooleanWrap();
    private DomainDTO domainDTO;
    private DomainFormADMIN domainForm;

    public DOMAINSWidget(HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        sFields = sysFields;
        sysMsgs = sysMsg;
        resultsPanel = new ResultsContainerADMIN();
        fixGwtNav();
        searchMenu = new SearchHeaderDOMAIN(sysMsg);
        add(searchMenu);
        add(resultsPanel);
        domainsCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page35");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Resources retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                }
                History.newItem("p35loaded");
            }
        };
        domainAddedCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    MainEntryPoint.statusPanel.setMessage("message", "Content added...");
                } else {
                    MainEntryPoint.statusPanel.setMessage("error", "Content not added...");

                }
                History.newItem("page35");
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page35");
            }
        };
        getDomainDetailsCallback = new AsyncCallback<DomainDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page35");
            }

            @Override
            public void onSuccess(DomainDTO result) {
                isEdited.setVal(false);
                domainDTO = result;
                refreshContentFromLanguageDTO();
                History.newItem("p35loaded");
            }
        };
        getDomainDetailsSaveCallback = new AsyncCallback<DomainDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("p35notSaved");
            }

            @Override
            public void onSuccess(DomainDTO result) {
                isEdited.setVal(false);
                domainDTO = result;
                refreshContentFromLanguageDTO();
                MainEntryPoint.statusPanel.setMessage("message", "Content updated...");
                History.newItem("page35");
            }
        };
        domainAddCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page35");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Domain retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.clear();
                    resultsPanel.elementDetails.clear();
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    History.newItem("p35loaded");
                } else {
                    new MyDialog("Would you like to add a new Doamin?", 0, "p35add").show();
                }
            }
        };
        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, "p35add").show();
                } else {
                    History.newItem("p35add");
                }
            }
        });

        resultsPanel.adjustSize(0.3f, 0.7f);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("DM")) {
                    if (isEdited.getVal()) {
                    } else {
                        long domID = Long.valueOf(command.substring(2));
                        if (isEdited.getVal()) {
                            new MyDialog("You have edited this user. Are you sure that you want to abort all the modifications?", 1, command).show();
                        } else {
                            getService().getDomainDetails(domID, getDomainDetailsCallback);
                        }
                    }
                } else {
                    switch (command) {
                        case "page35":
                            commandInit();
                            break;
                        case "p35escape":
                            commandEscape();
                            break;
                        case "p35add":
                            commandAdd();
                            break;
                    }
                }
            }
        });
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void commandAdd() {
        resultsPanel.elementDetails.clear();
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Adding a new entry, please wait...");
        isEdited.setVal(false);
        getService().getDomainsDetails(searchMenu.addField.getText(), domainAddCallback);
    }

    public void refreshContentFromLanguageDTO() {
        resultsPanel.elementDetails.clear();
        if (domainDTO != null) {
            domainForm = new DomainFormADMIN(sFields, isEdited, sysMsgs);
            resultsPanel.elementDetails.setWidget(domainForm);
            domainForm.adjustSize(resultsPanel.elementDetails.getOffsetWidth());
            domainForm.setContentFromDomainDTO(domainDTO);
            domainForm.save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        MainEntryPoint.statusPanel.setMessage("warning", "Updating content...");
                        domainForm.setDomainDTOFromContent(domainDTO);
                        getService().AdminUpdateDomain(domainDTO, getDomainDetailsSaveCallback);
                    } else {
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing changed...");
                    }
                }
            });
            domainForm.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this reource. Are you sure that you want to abort all the modifications?", 1, "p35escape").show();
                    } else {
                        domainForm.removeFromParent();
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing has changed...");
                    }
                }
            });
            domainForm.delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you aure that you want to delete this domain?", 2, "").show();
                }
            });
        } else {
            resultsPanel.elementDetails.setWidget(new Label("Language details object is null, something is worng"));
        }
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
                    submit.setText(sysMsgs.get(GuiConstant.BTN_ABORT));
                    break;
                case 2:
                    submit.setText(sysMsgs.get(GuiConstant.BTN_DELETE));
                    break;
            }
            submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    switch (call) {
                        case 0:
                            createNewDomain();
                            break;
                        case 1:
                            escapeDomain(action);
                            break;
                        case 2:
                            deleteDomain();
                            break;
                    }
                }
            });
            Button cancel = new Button(sysMsgs.get(GuiConstant.BTN_CANCEL));
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    History.newItem("p35cancelled");
                }
            });
            Button save = new Button(sysMsgs.get(GuiConstant.BTN_SAVE));
            save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    domainForm.save.setEnabled(false);
                    domainForm.setContentFromDomainDTO(domainDTO);
                    isEdited.setVal(false);
                    getService().AdminSaveDomain(domainDTO, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            domainForm.save.setEnabled(true);
                            resultsPanel.elementDetails.clear();
                            resultsPanel.elementDetails.setWidget(new Label("Communication failed"));
                            History.newItem("p35notsaved");
                        }

                        @Override
                        public void onSuccess(String result) {
                            domainForm.save.setEnabled(true);
                            if (result != null) {
                                if (action.contains("DM")) {
                                    String domID = action.substring(2);
                                    getService().getDomainDetails(Long.valueOf(domID), getDomainDetailsCallback);
                                } else {
                                    History.newItem(action);
                                }
                            } else {
                                History.newItem("p35notsaved");
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
            if (call != 1) {
                save.setEnabled(false);
            }
        }
    }

    private void commandInit() {
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        resultsPanel.sideRes.clear();
        getService().getDomainsDetails("", domainsCallback);
    }

    public void escapeDomain(String action) {
        isEdited.setVal(false);
        if (action.contains("DM")) {
            getService().getDomainDetails(Long.valueOf(action.substring(2)), getDomainDetailsCallback);
        } else {
            History.newItem(action);
        }
    }

    public void createNewDomain() {
        getDomainFromHeader();
        getService().createDomain(domainDTO, domainAddedCallback);
    }

    public void getDomainFromHeader() {
        domainDTO = new DomainDTO();
        domainDTO.setDomainDefaultName(searchMenu.addField.getText());
    }

    public void deleteDomain() {
        if (!domainDTO.getDomainDefaultName().isEmpty()) {
            getService().deleteDomain(domainDTO.getIdDomain(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    MainEntryPoint.statusPanel.setMessage("error", "Could not remove domain");
                }

                @Override
                public void onSuccess(String result) {
                    MainEntryPoint.statusPanel.setMessage("message", "Domain removed successfully");
                    domainForm.removeFromParent();
                    History.newItem("page35");
                }
            });
        }
    }

    private void commandEscape() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Changes cancelled successfully");
        resultsPanel.elementDetails.clear();
        isEdited.setVal(false);
        History.newItem("p35escaped");
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
