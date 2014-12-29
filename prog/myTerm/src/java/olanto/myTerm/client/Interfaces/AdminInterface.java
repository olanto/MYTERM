/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.ApproveWidget;
import olanto.myTerm.client.Widgets.WorkspaceWidget;
import olanto.myTerm.client.Widgets.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class AdminInterface extends TabPanel {

    private MyTermSearchWidget bpan;
    private WorkspaceWidget wpan;
    private ApproveWidget apan;

    public AdminInterface(long ownerID) {
        bpan = new MyTermSearchWidget(ownerID);
        wpan = new WorkspaceWidget(ownerID);
        apan = new ApproveWidget(ownerID);
        add(bpan, "Term Search");
        add(wpan, "Workspace");
        add(apan, "To approve");
        add(new HTML("Under construction!!!"), "System Administration");

        setStyleName("tabPanel");
        selectTab(0);
    }
}
