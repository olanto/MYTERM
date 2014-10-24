/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.WorkspaceWidget;
import olanto.myTerm.client.Widgets.ApproveWidget;
import olanto.myTerm.client.Widgets.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class AdminInterface extends TabPanel {

    private MyTermSearchWidget bpan = new MyTermSearchWidget();
    private WorkspaceWidget wpan = new WorkspaceWidget();
    private ApproveWidget apan = new ApproveWidget();

    public AdminInterface() {
        add(bpan, "Term Search");
        add(wpan, "Workspace");
        add(apan, "To approve");
        add(new HTML("Under construction!!!"), "System Administration");
        selectTab(0);
        setStyleName("tabPanel");
    }
}
