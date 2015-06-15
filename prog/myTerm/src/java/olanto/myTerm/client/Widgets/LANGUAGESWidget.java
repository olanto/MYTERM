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
import olanto.myTerm.client.ContainerPanels.SearchHeaderLANGUAGE;
import olanto.myTerm.client.Forms.LanguageFormADMIN;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.LanguageDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class LANGUAGESWidget extends VerticalPanel {

    private static SearchHeaderLANGUAGE searchMenu;
    private static ResultsContainerADMIN resultsPanel;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    private static AsyncCallback<String> languagesCallback;
    private static AsyncCallback<String> languagesAddCallback;
    private static AsyncCallback<String> languageAddedCallback;
    private static AsyncCallback<LanguageDTO> getLanguageDetailsSaveCallback;
    private static AsyncCallback<LanguageDTO> getLanguageDetailsCallback;
    public BooleanWrap isEdited = new BooleanWrap();
    private LanguageDTO languageDTO;
    private LanguageFormADMIN languageForm;

    public LANGUAGESWidget(HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        sFields = sysFields;
        sysMsgs = sysMsg;
        resultsPanel = new ResultsContainerADMIN();
        fixGwtNav();
        searchMenu = new SearchHeaderLANGUAGE(sysMsg);
        add(searchMenu);
        add(resultsPanel);
        languagesCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page32");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Languages retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                }
                History.newItem("p32loaded");
            }
        };
        languageAddedCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    MainEntryPoint.statusPanel.setMessage("message", "Content added...");
                } else {
                    MainEntryPoint.statusPanel.setMessage("error", "Content not added...");

                }
                History.newItem("page32");
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page32");
            }
        };
        getLanguageDetailsCallback = new AsyncCallback<LanguageDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page32");
            }

            @Override
            public void onSuccess(LanguageDTO result) {
                isEdited.setVal(false);
                languageDTO = result;
                refreshContentFromLanguageDTO();
                History.newItem("p32loaded");
            }
        };
        getLanguageDetailsSaveCallback = new AsyncCallback<LanguageDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("p32notSaved");
            }

            @Override
            public void onSuccess(LanguageDTO result) {
                isEdited.setVal(false);
                languageDTO = result;
                refreshContentFromLanguageDTO();
                MainEntryPoint.statusPanel.setMessage("message", "Content updated...");
                History.newItem("page32");
            }
        };
        languagesAddCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page32");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Language retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.clear();
                    resultsPanel.elementDetails.clear();
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    History.newItem("p32loaded");
                } else {
                    new MyDialog("Would you like to add a new Language?", 0, "p32add").show();
                }
            }
        };
        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, "p32add").show();
                } else {
                    History.newItem("p32add");
                }
            }
        });

        resultsPanel.adjustSize(0.5f, 0.5f);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("LM")) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this user. Are you sure that you want to abort all the modifications?", 1, command).show();
                    } else {
                        getService().getLanguageDetails(command.substring(2), getLanguageDetailsCallback);
                    }
                } else {
                    switch (command) {
                        case "page32":
                            commandInit();
                            break;
                        case "p32escape":
                            commandEscape();
                            break;
                        case "p32add":
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
        getService().getLanguagesDetails(searchMenu.idField.getText(),
                searchMenu.nameField.getText(),
                languagesAddCallback);
    }

    public void refreshContentFromLanguageDTO() {
        resultsPanel.elementDetails.clear();
        if (languageDTO != null) {
            languageForm = new LanguageFormADMIN(sFields, isEdited, sysMsgs);
            resultsPanel.elementDetails.setWidget(languageForm);
            languageForm.adjustSize(resultsPanel.elementDetails.getOffsetWidth());
            languageForm.setContentFromLanguageDTO(languageDTO);
            languageForm.save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        MainEntryPoint.statusPanel.setMessage("warning", "Updating content...");
                        languageForm.setLanguageDTOFromContent(languageDTO);
                        getService().AdminUpdateLanguage(languageDTO, getLanguageDetailsSaveCallback);
                    } else {
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing changed...");
                    }
                }
            });
            languageForm.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this reource. Are you sure that you want to abort all the modifications?", 1, "p32scape").show();
                    } else {
                        languageForm.removeFromParent();
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing has changed...");
                    }
                }
            });
            languageForm.delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you aure that you want to delete this language?", 2, "").show();
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
                            createNewLanguage();
                            break;
                        case 1:
                            escapeLanguage(action);
                            break;
                        case 2:
                            deleteLanguage();
                            break;
                    }
                }
            });
            Button cancel = new Button(sysMsgs.get(GuiConstant.BTN_CANCEL));
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    History.newItem("p32cancelled");
                }
            });
            Button save = new Button(sysMsgs.get(GuiConstant.BTN_SAVE));
            save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    languageForm.save.setEnabled(false);
                    languageForm.setLanguageDTOFromContent(languageDTO);
                    isEdited.setVal(false);
                    getService().AdminSaveLanguage(languageDTO, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            languageForm.save.setEnabled(true);
                            resultsPanel.elementDetails.clear();
                            resultsPanel.elementDetails.setWidget(new Label("Communication failed"));
                            History.newItem("p32notsaved");
                        }

                        @Override
                        public void onSuccess(String result) {
                            languageForm.save.setEnabled(true);
                            if (result != null) {
                                if (action.contains("LM")) {
                                    String languageID = action.substring(2);
                                    getService().getLanguageDetails(languageID, getLanguageDetailsCallback);
                                } else {
                                    History.newItem(action);
                                }
                            } else {
                                History.newItem("p32notsaved");
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

    public void escapeLanguage(String action) {
        isEdited.setVal(false);
        if (action.contains("LM")) {
            getService().getLanguageDetails(action.substring(2), getLanguageDetailsCallback);
        } else {
            History.newItem(action);
        }
    }

    public void createNewLanguage() {
        getLanguageFromHeader();
        getService().createLanguage(languageDTO, languageAddedCallback);
    }

    public void getLanguageFromHeader() {
        languageDTO = new LanguageDTO();
        languageDTO.setIdLanguage(searchMenu.idField.getText());
        languageDTO.setLanguageDefaultName(searchMenu.nameField.getText());
    }

    public void deleteLanguage() {
        if (!languageDTO.getIdLanguage().isEmpty()) {
            getService().deleteLanguage(languageDTO.getIdLanguage(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    MainEntryPoint.statusPanel.setMessage("error", "Could not remove Language");
                }

                @Override
                public void onSuccess(String result) {
                    MainEntryPoint.statusPanel.setMessage("message", "Language removed successfully");
                    languageForm.removeFromParent();
                    History.newItem("page32");
                }
            });
        }
    }

    private void commandInit() {
        isEdited.setVal(false);
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        resultsPanel.sideRes.clear();
        getService().getLanguagesDetails("", "", languagesCallback);
    }

    private void commandEscape() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Changes cancelled successfully");
        resultsPanel.elementDetails.clear();
        isEdited.setVal(false);
        History.newItem("p32escaped");
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
