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
import java.util.Map;
import olanto.myTerm.shared.GuiConstant;
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
    public static HashMap<String, String> sysMsg;
    private AsyncCallback<Map<String, SysFieldDTO>> getSysFields;
    private AsyncCallback<Map<String, String>> getSysMsg;

    public AdminInterface(final long ownerID) {
        sysFields = new HashMap<>();
        sysMsg = new HashMap<>();
        getSysFields = new AsyncCallback<Map<String, SysFieldDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Could not get system fields values");
            }

            @Override
            public void onSuccess(Map<String, SysFieldDTO> result) {
                sysFields.putAll(result);
                getService().getSysMsgByLang(GuiConstant.INTERFACE_LANG, getSysMsg);
            }
        };
        getSysMsg = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Could not get system fields values");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                sysMsg.putAll(result);
                bpan = new READERWidget(ownerID, sysMsg);
                wpan = new REDACTORWidget(ownerID, sysFields, sysMsg);
                apan = new REVISORWidget(ownerID, sysFields, sysMsg);
                add(bpan, "Term Search");
                add(wpan, "Workspace");
                add(apan, "To approve");
                add(new HTML("Under construction!!!"), "System Administration");
                setStyleName("tabPanel");
                selectTab(0);
            }
        };
        getService().getSysFieldsByLang(GuiConstant.INTERFACE_LANG, getSysFields);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }
}
