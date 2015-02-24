/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Widgets;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.TabPanel;
import java.util.HashMap;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class ADMINPANEL extends TabPanel {

    private USERSWidget upan;
    private RESOURCESWidget rpan;
    private LANGUAGESWidget lpan;
    private USERS_RESOURCESWidget urpan;
    private USERS_LANGUAGESWidget ulpan;
    private DOMAINSWidget dpan;
    private ENTRIESWidget epan;

    public ADMINPANEL(HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        upan = new USERSWidget(sysFields, sysMsg);
        rpan = new RESOURCESWidget(sysFields, sysMsg);
        lpan = new LANGUAGESWidget(sysFields, sysMsg);
        urpan = new USERS_RESOURCESWidget(sysFields, sysMsg);
        ulpan = new USERS_LANGUAGESWidget(sysFields, sysMsg);
        dpan = new DOMAINSWidget(sysFields, sysMsg);
        epan = new ENTRIESWidget(sysFields, sysMsg);
        add(upan, sysMsg.get(GuiConstant.TAB_USERS));
        add(rpan, sysMsg.get(GuiConstant.TAB_RESOURCES));
        add(lpan, sysMsg.get(GuiConstant.TAB_LANGUAGES));
        add(urpan, sysMsg.get(GuiConstant.TAB_USERS_RESOURCES));
        add(ulpan, sysMsg.get(GuiConstant.TAB_USERS_LANGUAGES));
        add(dpan, sysMsg.get(GuiConstant.TAB_DOMAINS));
        add(epan, sysMsg.get(GuiConstant.TAB_ENTRIES));
        setStyleName("tabPanel");
        this.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                History.newItem("page3" + event.getSelectedItem());
            }
        });
    }
}
