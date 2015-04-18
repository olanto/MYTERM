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
import olanto.myTerm.client.ContainerPanels.SearchHeaderRESOURCE;
import olanto.myTerm.client.Forms.ResourceFormADMIN;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.ResourceDTO;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class RESOURCESWidget extends VerticalPanel {

    private static SearchHeaderRESOURCE searchMenu;
    private static ResultsContainerADMIN resultsPanel;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    private static AsyncCallback<String> resourcesCallback;
    private static AsyncCallback<String> resourcesAddCallback;
    private static AsyncCallback<String> resourceAddedCallback;
    private static AsyncCallback<ResourceDTO> getResourceDetailsSaveCallback;
    private static AsyncCallback<ResourceDTO> getResourceDetailsCallback;
    public BooleanWrap isEdited = new BooleanWrap();
    private ResourceDTO resourceDTO;
    private ResourceFormADMIN resourceForm;

    public RESOURCESWidget(HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        sFields = sysFields;
        sysMsgs = sysMsg;
        resultsPanel = new ResultsContainerADMIN();
        fixGwtNav();
        searchMenu = new SearchHeaderRESOURCE(sysMsg);
        add(searchMenu);
        add(resultsPanel);
        resourcesCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page31");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Resources retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.elementDetails.clear();
                    resultsPanel.sideRes.clear();
                    resultsPanel.sideRes.setWidget(new HTML(result));
                }
                History.newItem("p31loaded");
            }
        };
        resourceAddedCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    MainEntryPoint.statusPanel.setMessage("message", "Content added...");
                } else {
                    MainEntryPoint.statusPanel.setMessage("error", "Content not added...");

                }
                History.newItem("page31");
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
                History.newItem("page31");
            }
        };
        getResourceDetailsCallback = new AsyncCallback<ResourceDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page31");
            }

            @Override
            public void onSuccess(ResourceDTO result) {
                isEdited.setVal(false);
                resourceDTO = result;
                refreshContentFromResourceDTO();
                History.newItem("p31loaded");
            }
        };
        getResourceDetailsSaveCallback = new AsyncCallback<ResourceDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("p31notSaved");
            }

            @Override
            public void onSuccess(ResourceDTO result) {
                isEdited.setVal(false);
                resourceDTO = result;
                refreshContentFromResourceDTO();
                MainEntryPoint.statusPanel.setMessage("message", "Content updated...");
                History.newItem("page31");
            }
        };
        resourcesAddCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page31");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Resource retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    History.newItem("p31loaded");
                } else {
                    new MyDialog("Would you like to add a new Resource?", 0, "p31add").show();
                }
            }
        };
        searchMenu.btnAdd.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (isEdited.getVal()) {
                    new MyDialog("You have edited this entry. Are you sure that you want to abort all the modifications?", 1, "p31add").show();
                } else {
                    History.newItem("p31add");
                }
            }
        });

        resultsPanel.adjustSize(0.5f, 0.5f);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("RM")) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this user. Are you sure that you want to abort all the modifications?", 1, command).show();
                    } else {
                        long resID = Long.parseLong(command.substring(2));
                        getService().getResourceDetails(resID, getResourceDetailsCallback);
                    }
                } else {
                    switch (command) {
                        case "page31":
                            commandInit();
                            break;
                        case "p31escape":
                            commandEscape();
                            break;
                        case "p31add":
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
        getService().getResourcesDetails(searchMenu.rsrcField.getText(),
                searchMenu.rsrcPrivay.getValue(searchMenu.rsrcPrivay.getSelectedIndex()),
                resourcesAddCallback);
    }

    private void commandInit() {
        isEdited.setVal(false);
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        getService().getResourcesDetails("", "", resourcesCallback);
    }

    private void commandEscape() {
        searchMenu.btnAdd.setEnabled(true);
        MainEntryPoint.statusPanel.setMessage("message", "Changes cancelled successfully");
        resultsPanel.elementDetails.clear();
        isEdited.setVal(false);
        History.newItem("p31escaped");
    }

    public void refreshContentFromResourceDTO() {
        resultsPanel.elementDetails.clear();
        if (resourceDTO != null) {
            resourceForm = new ResourceFormADMIN(sFields, isEdited, sysMsgs);
            resultsPanel.elementDetails.setWidget(resourceForm);
            resourceForm.adjustSize(resultsPanel.elementDetails.getOffsetWidth());
            resourceForm.setContentFromResourceDTO(resourceDTO, isEdited);
            resourceForm.save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        MainEntryPoint.statusPanel.setMessage("warning", "Updating content...");
                        resourceForm.setResourceDTOFromContent(resourceDTO);
                        getService().AdminUpdateResource(resourceDTO, getResourceDetailsSaveCallback);
                    } else {
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing changed...");
                    }
                }
            });
            resourceForm.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if (isEdited.getVal()) {
                        new MyDialog("You have edited this reource. Are you sure that you want to abort all the modifications?", 1, "p31scape").show();
                    } else {
                        resourceForm.removeFromParent();
                        MainEntryPoint.statusPanel.setMessage("warning", "Nothing has changed...");
                    }
                }
            });
            resourceForm.delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you aure that you want to delete this resource?", 2, "").show();
                }
            });
            resourceForm.addEvents(resourceDTO.getIdResource());
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
                            createNewResource();
                            break;
                        case 1:
                            escapeResource(action);
                            break;
                        case 2:
                            deleteResource();
                            break;
                    }
                }
            });
            Button cancel = new Button(sysMsgs.get(GuiConstant.BTN_CANCEL));
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    History.newItem("p31cancelled");
                }
            });
            Button save = new Button(sysMsgs.get(GuiConstant.BTN_SAVE));
            save.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    resourceForm.save.setEnabled(false);
                    resourceForm.setResourceDTOFromContent(resourceDTO);
                    isEdited.setVal(false);
                    getService().AdminSaveResource(resourceDTO, new AsyncCallback<String>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            resourceForm.save.setEnabled(true);
                            resultsPanel.elementDetails.clear();
                            resultsPanel.elementDetails.setWidget(new Label("Communication failed"));
                            History.newItem("p31notsaved");
                        }

                        @Override
                        public void onSuccess(String result) {
                            resourceForm.save.setEnabled(true);
                            if (result != null) {
                                if (action.contains("RM")) {
                                    long resourceID = Long.parseLong(action.substring(2));
                                    getService().getResourceDetails(resourceID, getResourceDetailsCallback);
                                } else {
                                    History.newItem(action);
                                }
                            } else {
                                History.newItem("p31notsaved");
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

    public void escapeResource(String action) {
        isEdited.setVal(false);
        if (action.contains("RM")) {
            long resourceID = Long.parseLong(action.substring(2));
            getService().getResourceDetails(resourceID, getResourceDetailsCallback);
        } else {
            History.newItem(action);
        }
    }

    public void createNewResource() {
        getResourceFromHeader();
        getService().createResource(resourceDTO, resourceAddedCallback);
    }

    public void getResourceFromHeader() {
        resourceDTO = new ResourceDTO();
        resourceDTO.setResourceName(searchMenu.rsrcField.getText());
        resourceDTO.setExtra("???");
        resourceDTO.setResourceNote("???");
        resourceDTO.setResourcePrivacy(searchMenu.rsrcPrivay.getValue(searchMenu.rsrcPrivay.getSelectedIndex()));
        resourceDTO.setIdOwner(-1l);
    }

    public void deleteResource() {
        if (resourceDTO.getIdResource() > -1) {
            getService().deleteResource(resourceDTO.getIdResource(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    MainEntryPoint.statusPanel.setMessage("error", "Could not remove Resource");
                }

                @Override
                public void onSuccess(String result) {
                    MainEntryPoint.statusPanel.setMessage("message", "Resource removed successfully");
                    resourceForm.removeFromParent();
                    History.newItem("page31");
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
