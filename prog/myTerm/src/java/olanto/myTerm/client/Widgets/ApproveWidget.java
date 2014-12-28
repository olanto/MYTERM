/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
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
        addTermsCallback = new AsyncCallback<ConceptEntryDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                resultsPanel.termsDetails.setWidget(new Label("Communication failed"));
            }

            @Override
            public void onSuccess(ConceptEntryDTO result) {
                conceptEntryDTO = result;
//                Window.alert(conceptEntryDTO.toStringDTO());
                refreshContentFromConceptEntryDTOApp();
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
                } else if (event.getValue().contains("page2")) {
                    String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
                    if ((lan == null) || (lan.isEmpty())) {
                        lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
                    }
                    getService().getApproveElements(lan, ownerID, workspaceCallback);
                }
            }
        });
        resultsPanel.adjustSize(0.25f, 0.3f);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    public void refreshContentFromConceptEntryDTOApp() {
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
        } else {
            resultsPanel.termsDetails.setWidget(new Label("Concept details object is null, something is worng"));
        }
        String lan = searchMenu.langSrc.getValue(searchMenu.langSrc.getSelectedIndex());
        if ((lan == null) || (lan.isEmpty())) {
            lan = Cookies.getCookie(MyTermCookiesNamespace.MyTermIDlangSrc);
        }
        getService().getApproveElements(lan, ownerID, workspaceCallback);
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
