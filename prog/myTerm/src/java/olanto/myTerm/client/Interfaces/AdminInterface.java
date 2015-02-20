/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import java.util.HashMap;
import olanto.myTerm.client.Widgets.REVISORWidget;
import olanto.myTerm.client.Widgets.REDACTORWidget;
import olanto.myTerm.client.Widgets.READERWidget;
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class AdminInterface extends TabPanel {

    private READERWidget bpan;
    private REDACTORWidget wpan;
    private REVISORWidget apan;

    public AdminInterface(final long ownerID, HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        bpan = new READERWidget(ownerID, sysMsg);
        wpan = new REDACTORWidget(ownerID, sysFields, sysMsg);
        apan = new REVISORWidget(ownerID, sysFields, sysMsg);
        add(bpan, sysMsg.get(GuiConstant.SEARCH_PANEL));
        add(wpan, sysMsg.get(GuiConstant.REDACTION_PANEL));
        add(apan, sysMsg.get(GuiConstant.REVISION_PANEL));
        add(new HTML("Under construction!!!"), sysMsg.get(GuiConstant.ADMINISTRATION_PANEL));
        setStyleName("tabPanel");
        selectTab(0);
    }
}
