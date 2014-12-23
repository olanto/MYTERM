/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
import java.util.Date;
import olanto.myTerm.client.ContainerPanels.ResultsContainerApprover;
import olanto.myTerm.client.ContainerPanels.SearchHeaderWorkspace;
import olanto.myTerm.client.ContainerPanels.StatusPanel;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.Forms.ConceptForm;
import olanto.myTerm.client.Forms.LangSetForm;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.ConceptEntryDTO;
import olanto.myTerm.shared.LangEntryDTO;

/**
 *
 * @author simple
 */
public class ApproveWidget extends VerticalPanel {

    private static SearchHeaderWorkspace searchMenu;
    private static ResultsContainerApprover resultsPanel = new ResultsContainerApprover();
    private static AsyncCallback<String> termAddCallback;
    private static AsyncCallback<String> deleteCallback;
    private static AsyncCallback<String> deleteWSCallback;
    private static AsyncCallback<String> workspaceCallback;
    private static AsyncCallback<ConceptEntryDTO> addTermsCallback;
    private static ConceptEntryDTO conceptEntryDTO;
    private static ConceptForm addcpt;
    private static LangSetForm addterms;
    private long ownerID;

    public ApproveWidget(long idOwner) {
        ownerID = idOwner;
        fixGwtNav();
        searchMenu = new SearchHeaderWorkspace(ownerID);
        add(searchMenu);
        add(resultsPanel);
        // Create an asynchronous callback to handle the result.
        termAddCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    resultsPanel.addnewcpt.setVisible(true);
                    getService().getApproveElements(searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), ownerID, workspaceCallback);
                } else {
                    new MyDialog("Concept not found would you like to create a new concept?", 0).show();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        // Create an asynchronous callback to handle the result.
        deleteCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.clear();
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                } else {
                    resultsPanel.sideRes.clear();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        // Create an asynchronous callback to handle the result.
        deleteWSCallback = new AsyncCallback<String>() {
            @Override
            public void onSuccess(String result) {
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.sideRes.setWidget(new HTML(result));
                    getService().getAddResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, deleteCallback);
                } else {
                    resultsPanel.sideRes.clear();
                }
            }

            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
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
            }

            @Override
            public void onFailure(Throwable caught) {
                searchMenu.btnAdd.setEnabled(true);
                resultsPanel.sideRes.setWidget(new Label("Communication failed"));
            }
        };
        addTermsCallback = new AsyncCallback<ConceptEntryDTO>() {
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
                History.newItem("page1");
                StatusPanel.clearMessages();
                resultsPanel.sideRes.clear();
                resultsPanel.termsDetails.clear();
                resultsPanel.conceptDetails.clear();
                searchMenu.btnAdd.setEnabled(false);
                if ((searchMenu.searchField.getText().isEmpty()) || (searchMenu.searchField.getText().equals("%"))) {
                    Window.alert("Please indicate the term's form");
                    searchMenu.btnAdd.setEnabled(true);
                } else {
                    getService().getAddResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallback);
                }
            }
        });
        // Listen for the button clicks
        searchMenu.searchField.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
                    History.newItem("page1");
                    StatusPanel.clearMessages();
                    resultsPanel.sideRes.clear();
                    resultsPanel.termsDetails.clear();
                    resultsPanel.conceptDetails.clear();
                    searchMenu.btnAdd.setEnabled(false);
                    if ((searchMenu.searchField.getText().isEmpty()) || (searchMenu.searchField.getText().contains("%"))) {
                        Window.alert("Please indicate the term's form");
                        searchMenu.btnAdd.setEnabled(true);
                    } else {
                        getService().getAddResult(searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallback);
                    }
                }
            }
        });
        searchMenu.searchField.setFocus(true);
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                StatusPanel.clearMessages();
                resultsPanel.conceptDetails.clear();
                resultsPanel.termsDetails.clear();
                if (event.getValue().contains("new")) {
                    long conceptID = Long.parseLong(event.getValue().substring(3));
                    getService().getAddDetailsForConcept(conceptID, ownerID, addTermsCallback);
                }
            }
        });
        searchMenu.langSrc.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                getService().getApproveElements(searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), ownerID, workspaceCallback);
            }
        });
        resultsPanel.addnewcpt.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                History.newItem("page1");
                createNewConceptEntry();
            }
        });
        resultsPanel.adjustSize(0.25f, 0.3f);
        getService().getApproveElements(Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc), ownerID, workspaceCallback);
    }

    public void refreshContentFromConceptEntryDTO() {
        resultsPanel.conceptDetails.clear();
        resultsPanel.termsDetails.clear();
        if (conceptEntryDTO != null) {
            addcpt = new ConceptForm(ownerID);
            resultsPanel.conceptDetails.setWidget(addcpt);
            addcpt.adjustSize(resultsPanel.conceptDetails.getOffsetWidth() - 70);
            addcpt.refreshContentFromConceptEntryDTO(conceptEntryDTO.concept);
            if (!conceptEntryDTO.listlang.isEmpty()) {
                addterms = new LangSetForm(ownerID);
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
                    getService().UpdateConceptEntry(conceptEntryDTO, ownerID, addTermsCallback);
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                }
            });
            addcpt.submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    addcpt.save.setEnabled(false);
                    getConceptEntryDTOFromWidget();
//                    Window.alert(conceptEntryDTO.toStringDTO());
                    getService().submitConceptEntry(conceptEntryDTO, searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), ownerID, deleteWSCallback);
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                }
            });
            addcpt.delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    new MyDialog("Are you sure that you want to remove this concept and all its associated terms?", 1).show();
                    resultsPanel.conceptDetails.clear();
                    resultsPanel.termsDetails.clear();
                    History.newItem("page1");
                }
            });
            addcpt.escape.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    addcpt.clearAllText();
                    addterms.clearAllText();
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
        getService().createConceptEntry(conceptEntryDTO, searchMenu.searchField.getText(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), searchMenu.rsrc.getValue(searchMenu.rsrc.getSelectedIndex()), searchMenu.dom.getItemText(searchMenu.dom.getSelectedIndex()), ownerID, termAddCallback);
    }

    public void deleteConceptEntry() {
        getService().DeleteConceptEntry(conceptEntryDTO.concept.getIdConcept(), searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex()), ownerID, deleteWSCallback);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private static void getConceptEntryDTOFromWidget() {
        addcpt.updateConceptDTOFromContent(conceptEntryDTO.concept);
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
            if (call == 0) {
                submit.setText("Create");
            } else {
                submit.setText("Delete");
            }
            submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    if (call == 0) {
                        createNewConceptEntry();
                    } else {
                        deleteConceptEntry();
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

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
