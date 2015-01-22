/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import java.util.HashMap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.client.Widgets.REVISORWidget;
import olanto.myTerm.client.Widgets.REDACTORWidget;
import olanto.myTerm.client.Widgets.READERWidget;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class AdminInterface extends TabPanel {

    private READERWidget bpan;
    private REDACTORWidget wpan;
    private REVISORWidget apan;
    public static HashMap<String, SysFieldDTO> sysFields;
    private AsyncCallback<HashMap<String, SysFieldDTO>> getSysFields;

    public AdminInterface(final long ownerID) {

        getSysFields = new AsyncCallback<HashMap<String, SysFieldDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Could not get system fields values");
            }

            @Override
            public void onSuccess(HashMap<String, SysFieldDTO> result) {
                sysFields = result;
                bpan = new READERWidget(ownerID);
                wpan = new REDACTORWidget(ownerID, result);
                apan = new REVISORWidget(ownerID);
                add(bpan, "Term Search");
                add(wpan, "Workspace");
                add(apan, "To approve");
                add(new HTML("Under construction!!!"), "System Administration");
                setStyleName("tabPanel");
                selectTab(0);
            }
        };
        getService().getSysFieldsByLang("EN", getSysFields);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }
}
