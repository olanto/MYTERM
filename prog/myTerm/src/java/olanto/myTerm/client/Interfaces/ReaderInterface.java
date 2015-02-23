/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.TabPanel;
import java.util.HashMap;
import olanto.myTerm.client.Widgets.READERWidget;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class ReaderInterface extends TabPanel {

    private READERWidget bpan;

    public ReaderInterface(final long ownerID, HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {

        bpan = new READERWidget(ownerID, sysMsg);
        add(bpan, sysMsg.get(GuiConstant.TAB_SEARCH));
        setStyleName("tabPanel");
        selectTab(0);
    }
}
