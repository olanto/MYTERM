/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TabPanel;
import java.util.HashMap;
import java.util.Map;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.client.Widgets.READERWidget;

/**
 *
 * @author simple
 */
public class ReaderInterface extends TabPanel {

    private READERWidget bpan;
    public static HashMap<String, String> sysMsg;
    private AsyncCallback<Map<String, String>> getSysMsg;

    public ReaderInterface(final long ownerID) {
        sysMsg = new HashMap<>();
        getSysMsg = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Could not get system fields values");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                sysMsg.putAll(result);
                bpan = new READERWidget(ownerID, sysMsg);
                add(bpan, "Term Search");
                setStyleName("tabPanel");
                selectTab(0);
            }
        };
        getService().getSysMsgByLang(GuiConstant.INTERFACE_LANG, getSysMsg);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }
}
