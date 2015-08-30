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
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
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
import olanto.myTerm.client.HeaderPanels.SearchHeaderUSER_LANGUAGE;
import olanto.myTerm.client.Forms.UserLanguageFormADMIN;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.UserLanguageDTO;

/**
 *
 * @author nizar ghoula
 */
public class USERS_LANGUAGESWidget extends VerticalPanel {

    private static SearchHeaderUSER_LANGUAGE searchMenu;
    private static ResultsContainerADMIN resultsPanel;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    public BooleanWrap isEdited = new BooleanWrap();
    private static AsyncCallback<String> usersLanguagesCallback;
    private static AsyncCallback<String> userLanguageAddCallback;
    private static AsyncCallback<String> userLanguageAddedCallback;
    private static AsyncCallback<UserLanguageDTO> getUserLanguageDetailsSaveCallback;
    private static AsyncCallback<UserLanguageDTO> getUserLanguageDetailsCallback;
    private UserLanguageDTO userLanguageDTO;
    private UserLanguageFormADMIN userLanguageForm;

    public USERS_LANGUAGESWidget(HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        sFields = sysFields;
        sysMsgs = sysMsg;
        resultsPanel = new ResultsContainerADMIN();
        fixGwtNav();
        searchMenu = new SearchHeaderUSER_LANGUAGE(sysMsg);
        add(searchMenu);
        add(resultsPanel);
        usersLanguagesCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page34");
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
                History.newItem("p34loaded");
            }
        };

        userLanguageAddedCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    MainEntryPoint.statusPanel.setMessage("message", "Content added...");
                } else {
                    MainEntryPoint.statusPanel.setMessage("error", "Content not added...");

                }
                History.newItem("page34");
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page34");
            }
        };
        getUserLanguageDetailsCallback = new AsyncCallback<UserLanguageDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page34");
            }

            @Override
            public void onSuccess(UserLanguageDTO result) {
                isEdited.setVal(false);
                userLanguageDTO = result;
                refreshContentFromUserLanguageDTO();
                History.newItem("p34loaded");
            }
        };
        getUserLanguageDetailsSaveCallback = new AsyncCallback<UserLanguageDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("p34notSaved");
            }

            @Override
            public void onSuccess(UserLanguageDTO result) {
                isEdited.setVal(false);
                userLanguageDTO = result;
                refreshContentFromUserLanguageDTO();
                MainEntryPoint.statusPanel.setMessage("message", "Content updated...");
                History.newItem("page34");
            }
        };
        userLanguageAddCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page34");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Relation retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    History.newItem("p34loaded");
                } else {
                    new MyDialog("Would you like to add a new Relation?", 0, "p34add").show();
                }
            }
        };
        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, "p34add").show();
                } else {
                    History.newItem("p34add");
                }
            }
        });

        searchMenu.btnDispAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, "p33displayAll").show();
                } else {
                    History.newItem("p34displayAll");
                }
            }
        });

        searchMenu.btnAdd.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, "p34add").show();
                } else {
                    History.newItem("p34add");
                }
            }
        });

        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("UL")) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, command).show();
                    } else {
                        long linkID = Long.parseLong(command.substring(2));
                        getService().getUserLanguage(linkID, getUserLanguageDetailsCallback);
                    }
                } else {
                    switch (command) {
                        case "page34":
                            commandInit();
                            break;
                        case "p34escape":
                            commandEscape();
                            break;
                        case "p34add":
                            commandAdd();
                            break;
                        case "p34displayAll":
                            commandReload();
                            break;
                    }
                }
            }
        });
        resultsPanel.adjustSize(0.4f, 0.6f);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void commandInit() {
        isEdited.setVal(false);
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        getService().getUsersLanguagesDetails(Long.valueOf(searchMenu.ownerList.getValue(searchMenu.ownerList.getSelectedIndex())),
                searchMenu.langList.getValue(searchMenu.langList.getSelectedIndex()), usersLanguagesCallback);
    }

    private void commandReload() {
        isEdited.setVal(false);
        resultsPanel.sideRes.clear();
        searchMenu.ownerList.setSelectedIndex(0);
        searchMenu.langList.setSelectedIndex(0);
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        getService().getUsersLanguagesDetails(-1, "", usersLanguagesCallback);
    }

    private void commandAdd() {
        resultsPanel.elementDetails.clear();
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Adding a new entry, please wait...");
        isEdited.setVal(false);
        getService().getUsersLanguagesDetails(Long.valueOf(searchMenu.ownerList.getValue(searchMenu.ownerList.getSelectedIndex())),
                searchMenu.langList.getValue(searchMenu.langList.getSelectedIndex()),
                userLanguageAddCallback);
    }

    private void commandEscape() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Changes cancelled successfully");
        resultsPanel.elementDetails.clear();
        isEdited.setVal(false);
        History.newItem("p34escaped");
    }

    public void refreshContentFromUserLanguageDTO() {
        resultsPanel.elementDetails.clear();
        if (userLanguageDTO != null) {
            userLanguageForm = new UserLanguageFormADMIN(sFields, isEdited, sysMsgs);
            resultsPanel.elementDetails.setWidget(userLanguageForm);
            userLanguageForm.adjustSize(resultsPanel.elementDetails.getOffsetWidth());
            userLanguageForm.setContentFromLanguageDTO(userLanguageDTO, isEdited);
            userLanguageForm.save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        MainEntryPoint.statusPanel.setMessage("warning", "Updating content...");
                        userLanguageForm.setUserLanguageDTOFromContent(userLanguageDTO);
                        getService().AdminUpdateUserLanguage(userLanguageDTO, getUserLanguageDetailsSaveCallback);
                    } else {
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing changed...");
                    }
                }
            });
            userLanguageForm.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, "p34scape").show();
                    } else {
                        userLanguageForm.removeFromParent();
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing has changed...");
                    }
                }
            });
            userLanguageForm.delete.addClickHandler(new ClickHandler() {
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
                            createNewUserLanguage();
                            break;
                        case 1:
                            escapeUserLanguage(action);
                            break;
                        case 2:
                            deleteUserLanguage();
                            break;
                    }
                }
            });
            Button cancel = new Button(sysMsgs.get(GuiConstant.BTN_CANCEL));
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    History.newItem("p34cancelled");
                }
            });
            Button save = new Button(sysMsgs.get(GuiConstant.BTN_SAVE));
            save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    userLanguageForm.save.setEnabled(false);
                    userLanguageForm.setUserLanguageDTOFromContent(userLanguageDTO);
                    isEdited.setVal(false);
                    getService().AdminSaveUserLanguage(userLanguageDTO, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            userLanguageForm.save.setEnabled(true);
                            resultsPanel.elementDetails.clear();
                            resultsPanel.elementDetails.setWidget(new Label("Communication failed"));
                            History.newItem("p34notsaved");
                        }

                        @Override
                        public void onSuccess(String result) {
                            userLanguageForm.save.setEnabled(true);
                            if (result != null) {
                                if (action.contains("UL")) {
                                    long urID = Long.valueOf(action.substring(2));
                                    getService().getUserLanguage(urID, getUserLanguageDetailsCallback);
                                } else {
                                    History.newItem(action);
                                }
                            } else {
                                History.newItem("p34notsaved");
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

    public void escapeUserLanguage(String action) {
        isEdited.setVal(false);
        if (action.contains("UL")) {
            long urID = Long.valueOf(action.substring(2));
            getService().getUserLanguage(urID, getUserLanguageDetailsCallback);
        } else {
            History.newItem(action);
        }
    }

    public void createNewUserLanguage() {
        getUserLanguageFromHeader();
        getService().createUserLanguage(userLanguageDTO, userLanguageAddedCallback);
    }

    public void getUserLanguageFromHeader() {
        userLanguageDTO = new UserLanguageDTO();
        userLanguageDTO.setIdOwner(Long.valueOf(searchMenu.ownerList.getValue(searchMenu.ownerList.getSelectedIndex())));
        userLanguageDTO.setIdLang(searchMenu.langList.getValue(searchMenu.langList.getSelectedIndex()));
        userLanguageDTO.setId(-1l);
    }

    public void deleteUserLanguage() {
        if (!userLanguageDTO.getIdLang().isEmpty()) {
            getService().deleteUserLanguage(userLanguageDTO.getId(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    MainEntryPoint.statusPanel.setMessage("error", "Could not remove relation");
                }

                @Override
                public void onSuccess(String result) {
                    MainEntryPoint.statusPanel.setMessage("message", "Relation removed successfully");
                    userLanguageForm.removeFromParent();
                    History.newItem("p34displayAll");
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
