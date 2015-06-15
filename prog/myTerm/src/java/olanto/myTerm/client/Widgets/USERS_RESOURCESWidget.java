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
import olanto.myTerm.client.ContainerPanels.SearchHeaderUSER_RESOURCE;
import olanto.myTerm.client.Forms.UserResourceFormADMIN;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.UserResourceDTO;

/**
 *
 * @author simple
 */
public class USERS_RESOURCESWidget extends VerticalPanel {

    private static SearchHeaderUSER_RESOURCE searchMenu;
    private static ResultsContainerADMIN resultsPanel;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    public BooleanWrap isEdited = new BooleanWrap();
    private static AsyncCallback<String> usersResourcesCallback;
    private static AsyncCallback<String> userResourceAddCallback;
    private static AsyncCallback<String> userResourceAddedCallback;
    private static AsyncCallback<UserResourceDTO> getUserResourceDetailsSaveCallback;
    private static AsyncCallback<UserResourceDTO> getUserResourceDetailsCallback;
    private UserResourceDTO userResourceDTO;
    private UserResourceFormADMIN userResourceForm;

    public USERS_RESOURCESWidget(HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        sFields = sysFields;
        sysMsgs = sysMsg;
        resultsPanel = new ResultsContainerADMIN();
        fixGwtNav();
        searchMenu = new SearchHeaderUSER_RESOURCE(sysMsg);
        add(searchMenu);
        add(resultsPanel);
        usersResourcesCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page33");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Relations retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.elementDetails.clear();
                    resultsPanel.sideRes.clear();
                    resultsPanel.sideRes.setWidget(new HTML(result));
                }
                History.newItem("p33loaded");
            }
        };
        userResourceAddedCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    MainEntryPoint.statusPanel.setMessage("message", "Content added...");
                } else {
                    MainEntryPoint.statusPanel.setMessage("error", "Content not added...");

                }
                History.newItem("page33");
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page33");
            }
        };
        getUserResourceDetailsCallback = new AsyncCallback<UserResourceDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page33");
            }

            @Override
            public void onSuccess(UserResourceDTO result) {
                isEdited.setVal(false);
                userResourceDTO = result;
                refreshContentFromResourceDTO();
                History.newItem("p33loaded");
            }
        };
        getUserResourceDetailsSaveCallback = new AsyncCallback<UserResourceDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("p33notSaved");
            }

            @Override
            public void onSuccess(UserResourceDTO result) {
                isEdited.setVal(false);
                userResourceDTO = result;
                refreshContentFromResourceDTO();
                MainEntryPoint.statusPanel.setMessage("message", "Content updated...");
                History.newItem("page33");
            }
        };
        userResourceAddCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page33");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Relation retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    History.newItem("p33loaded");
                } else {
                    new MyDialog("Would you like to add a new Relation?", 0, "p33add").show();
                }
            }
        };
        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, "p33add").show();
                } else {
                    History.newItem("p33add");
                }
            }
        });
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("UR")) {
                    command = command.substring(2);
                    String[] ids = command.split(";");
                    long urID = Long.valueOf(ids[0]);
                    String role = ids[1];
                    getService().getUserResource(urID, role, getUserResourceDetailsCallback);
                } else {
                    switch (command) {
                        case "page33":
                            commandInit();
                            break;
                        case "p33escape":
                            commandEscape();
                            break;
                        case "p33add":
                            commandAdd();
                            break;
                    }
                }
            }
        });
        resultsPanel.adjustSize(0.5f, 0.5f);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void commandInit() {
        isEdited.setVal(false);
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        getService().getUsersResourcesDetails(-1, -1, " ", usersResourcesCallback);
    }

    private void commandAdd() {
        resultsPanel.elementDetails.clear();
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Adding a new entry, please wait...");
        isEdited.setVal(false);
        getService().getUsersResourcesDetails(Long.valueOf(searchMenu.ownerList.getValue(searchMenu.ownerList.getSelectedIndex())),
                Long.valueOf(searchMenu.rsrcList.getValue(searchMenu.rsrcList.getSelectedIndex())),
                searchMenu.ownerRole.getValue(searchMenu.ownerRole.getSelectedIndex()),
                userResourceAddCallback);
    }

    private void commandEscape() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Changes cancelled successfully");
        resultsPanel.elementDetails.clear();
        isEdited.setVal(false);
        History.newItem("p33escaped");
    }

    public void refreshContentFromResourceDTO() {
        resultsPanel.elementDetails.clear();
        if (userResourceDTO != null) {
            userResourceForm = new UserResourceFormADMIN(sFields, isEdited, sysMsgs);
            resultsPanel.elementDetails.setWidget(userResourceForm);
            userResourceForm.adjustSize(resultsPanel.elementDetails.getOffsetWidth());
            userResourceForm.setContentFromUserResourceDTO(userResourceDTO, isEdited);
            userResourceForm.save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        MainEntryPoint.statusPanel.setMessage("warning", "Updating content...");
                        userResourceForm.setUserResourceDTOFromContent(userResourceDTO);
                        getService().AdminUpdateUserResource(userResourceDTO, getUserResourceDetailsSaveCallback);
                    } else {
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing changed...");
                    }
                }
            });
            userResourceForm.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this reource. Are you sure that you want to abort all the modifications?", 1, "p33scape").show();
                    } else {
                        userResourceForm.removeFromParent();
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing has changed...");
                    }
                }
            });
            userResourceForm.delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you aure that you want to delete this relation?", 2, "").show();
                }
            });
        } else {
            resultsPanel.elementDetails.setWidget(new Label("Resource details object is null, something is worng"));
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
                            createNewUserResource();
                            break;
                        case 1:
                            escapeUserResource(action);
                            break;
                        case 2:
                            deleteUserResource();
                            break;
                    }
                }
            });
            Button cancel = new Button(sysMsgs.get(GuiConstant.BTN_CANCEL));
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    History.newItem("p33cancelled");
                }
            });
            Button save = new Button(sysMsgs.get(GuiConstant.BTN_SAVE));
            save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    userResourceForm.save.setEnabled(false);
                    userResourceForm.setUserResourceDTOFromContent(userResourceDTO);
                    isEdited.setVal(false);
                    getService().AdminSaveUserResource(userResourceDTO, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            userResourceForm.save.setEnabled(true);
                            resultsPanel.elementDetails.clear();
                            resultsPanel.elementDetails.setWidget(new Label("Communication failed"));
                            History.newItem("p33notsaved");
                        }

                        @Override
                        public void onSuccess(String result) {
                            userResourceForm.save.setEnabled(true);
                            if (result != null) {
                                if (action.contains("UR")) {
                                    String[] ids = action.substring(2).split("|");
                                    long urID = Long.parseLong(ids[0]);
                                    String role = ids[1];
                                    getService().getUserResource(urID, role, getUserResourceDetailsCallback);
                                } else {
                                    History.newItem(action);
                                }
                            } else {
                                History.newItem("p33notsaved");
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

    public void escapeUserResource(String action) {
        isEdited.setVal(false);
        if (action.contains("UR")) {
            String[] ids = action.substring(2).split("|");
            long urID = Long.parseLong(ids[0]);
            String role = ids[1];
            getService().getUserResource(urID, role, getUserResourceDetailsCallback);
        } else {
            History.newItem(action);
        }
    }

    public void createNewUserResource() {
        getUserResourceFromHeader();
        getService().createUserResource(userResourceDTO, userResourceAddedCallback);
    }

    public void getUserResourceFromHeader() {
        userResourceDTO = new UserResourceDTO();
        userResourceDTO.setIdOwner(Long.valueOf(searchMenu.ownerList.getValue(searchMenu.ownerList.getSelectedIndex())));
        userResourceDTO.setIdResource(Long.valueOf(searchMenu.rsrcList.getValue(searchMenu.rsrcList.getSelectedIndex())));
        userResourceDTO.setOwnerRole(searchMenu.ownerRole.getValue(searchMenu.ownerRole.getSelectedIndex()));
        userResourceDTO.setId(-1l);
    }

    public void deleteUserResource() {
        if (userResourceDTO.getIdResource() > -1) {
            getService().deleteResource(userResourceDTO.getIdResource(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    MainEntryPoint.statusPanel.setMessage("error", "Could not remove relation");
                }

                @Override
                public void onSuccess(String result) {
                    MainEntryPoint.statusPanel.setMessage("message", "Relation removed successfully");
                    userResourceForm.removeFromParent();
                    History.newItem("page33");
                }
            });
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
