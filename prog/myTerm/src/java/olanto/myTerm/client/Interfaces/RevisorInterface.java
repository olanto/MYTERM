/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.ApproveWidget;
import olanto.myTerm.client.Widgets.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class RevisorInterface extends TabPanel {

    private MyTermSearchWidget bpan;
    private ApproveWidget apan;

    public RevisorInterface(long ownerID) {
        bpan = new MyTermSearchWidget(ownerID);
        apan = new ApproveWidget(ownerID);
        add(bpan, "Term Search");
        add(apan, "To approve");
        setStyleName("tabPanel");
        selectTab(0);
    }
}
