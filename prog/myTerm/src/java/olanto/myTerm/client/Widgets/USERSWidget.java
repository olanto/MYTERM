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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import olanto.myTerm.client.ContainerPanels.ResultsContainerADMIN;
import olanto.myTerm.client.ContainerPanels.SearchHeaderUSER;
import olanto.myTerm.client.Forms.OwnerFormADMIN;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.OwnerDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class USERSWidget extends VerticalPanel {

    private static SearchHeaderUSER searchMenu;
    private static ResultsContainerADMIN resultsPanel;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    private static AsyncCallback<String> ownersCallback;
    private static AsyncCallback<String> userAddedCallback;
    private static AsyncCallback<OwnerDTO> getOwnerDetailsCallback;
    private static AsyncCallback<OwnerDTO> getOwnerDetailsSaveCallback;
    public BooleanWrap isEdited = new BooleanWrap();
    private OwnerDTO ownerDTO;
    private OwnerFormADMIN ownerForm;

    public USERSWidget(HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        sFields = sysFields;
        sysMsgs = sysMsg;
        resultsPanel = new ResultsContainerADMIN();
        fixGwtNav();
        searchMenu = new SearchHeaderUSER(sysMsg);
        add(searchMenu);
        add(resultsPanel);
        userAddedCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    MainEntryPoint.statusPanel.setMessage("message", "Content added...");
                } else {
                    MainEntryPoint.statusPanel.setMessage("error", "Content not added...");

                }
                History.newItem("page30");
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page30");
            }
        };
        getOwnerDetailsCallback = new AsyncCallback<OwnerDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page30");
            }

            @Override
            public void onSuccess(OwnerDTO result) {
                isEdited.setVal(false);
                ownerDTO = result;
                refreshContentFromOwnerDTO();
                History.newItem("p30loaded");
            }
        };
        getOwnerDetailsSaveCallback = new AsyncCallback<OwnerDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("p30notSaved");
            }

            @Override
            public void onSuccess(OwnerDTO result) {
                isEdited.setVal(false);
                ownerDTO = result;
                refreshContentFromOwnerDTO();
                MainEntryPoint.statusPanel.setMessage("message", "Content updated...");
                History.newItem("page30");
            }
        };
        ownersCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page30");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Owners retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                }
                History.newItem("p30loaded");
            }
        };
        resultsPanel.adjustSize(0.5f, 0.5f);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("UM")) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this user. Are you sure that you want to abort all the modifications?", 1, command).show();
                    } else {
                        long ownerID = Long.parseLong(command.substring(2));
                        getService().getOwnerDetails(ownerID, getOwnerDetailsCallback);
                    }
                } else {
                    switch (command) {
                        case "page30":
                            commandInit();
                            break;
                             case "p30escape":
                            commandEscape();
                            break;
                    }
                }
            }
        });
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void commandInit() {
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        resultsPanel.sideRes.clear();
        getService().getOwnersDetails("", "", "", ownersCallback);
    }
    private void commandEscape() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Changes cancelled successfully");
        resultsPanel.elementDetails.clear();
        isEdited.setVal(false);
        History.newItem("p30escaped");
    }

    public void refreshContentFromOwnerDTO() {
        resultsPanel.elementDetails.clear();
        if (ownerDTO != null) {
//            Window.alert(conceptEntryDTO.toStringDTO());
            ownerForm = new OwnerFormADMIN(sFields, isEdited, sysMsgs);
            resultsPanel.elementDetails.setWidget(ownerForm);
            ownerForm.adjustSize(resultsPanel.elementDetails.getOffsetWidth());
            ownerForm.setContentFromOwnerDTO(ownerDTO, isEdited);
            ownerForm.save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        MainEntryPoint.statusPanel.setMessage("warning", "Updating content...");
                        ownerForm.setOwnerDTOFromContent(ownerDTO);
                        getService().AdminUpdateUser(ownerDTO, getOwnerDetailsSaveCallback);
                    } else {
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing changed...");
                    }
                }
            });
            ownerForm.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this user. Are you sure that you want to abort all the modifications?", 1, "p30escape").show();
                    } else {
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing has changed...");
                    }
                }
            });
        } else {
            resultsPanel.elementDetails.setWidget(new Label("Owner details object is null, something is worng"));
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
            }
            submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    switch (call) {
                        case 0:
                            createNewUser();
                            break;
                        case 1:
                            escapeUser(action);
                            break;
                    }
                }
            });
            Button cancel = new Button(sysMsgs.get(GuiConstant.BTN_CANCEL));
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    History.newItem("p30cancelled");
                }
            });
            Button save = new Button(sysMsgs.get(GuiConstant.BTN_SAVE));
            save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    ownerForm.save.setEnabled(false);
                    ownerForm.setOwnerDTOFromContent(ownerDTO);
                    isEdited.setVal(false);
//                        Window.alert(conceptEntryDTO.toStringDTO());
                    getService().AdminSaveUser(ownerDTO, new AsyncCallback<OwnerDTO>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            ownerForm.save.setEnabled(true);
                            resultsPanel.elementDetails.clear();
                            resultsPanel.elementDetails.setWidget(new Label("Communication failed"));
                            History.newItem("p30notsaved");
                        }

                        @Override
                        public void onSuccess(OwnerDTO result) {
                            ownerForm.save.setEnabled(true);
                            if (result != null) {
                                if (action.contains("UM")) {
                                    long ownerID = Long.parseLong(action.substring(2));
                                    getService().getOwnerDetails(ownerID, getOwnerDetailsCallback);
                                } else {
                                    History.newItem(action);
                                }
                            } else {
                                History.newItem("p30notsaved");
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

    public void escapeUser(String action) {
        isEdited.setVal(false);
        if (action.contains("UM")) {
            long ownerID = Long.parseLong(action.substring(2));
            getService().getOwnerDetails(ownerID, getOwnerDetailsCallback);
        } else {
            History.newItem(action);
        }
    }

    public void createNewUser() {
        getUserFromHeader();
        getService().createUser(ownerDTO, userAddedCallback);
    }

    public void getUserFromHeader() {
        ownerDTO = new OwnerDTO();
        ownerDTO.setEmail(searchMenu.mailingField.getText());
        ownerDTO.setRole(searchMenu.ownerRole.getValue(searchMenu.ownerRole.getSelectedIndex()));
        ownerDTO.setStatus(searchMenu.ownerStatus.getValue(searchMenu.ownerStatus.getSelectedIndex()));
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
