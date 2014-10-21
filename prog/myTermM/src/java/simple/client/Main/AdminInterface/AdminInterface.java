/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.client.Main.AdminInterface;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import simple.client.Main.EditorWidget.WorkspaceWidget;
import simple.client.Main.ValidatorWidget.ApproveWidget;
import simple.client.Main.publicWidget.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class AdminInterface extends TabPanel {

    private MyTermSearchWidget bpan = new MyTermSearchWidget();
    private WorkspaceWidget wpan = new WorkspaceWidget();
    private ApproveWidget apan = new ApproveWidget();

    public AdminInterface() {
        add(bpan, "Browse your Resources");
        add(wpan, "Workspace");
        add(apan, "To approve");
        add(new HTML("Under construction!!!"), "System Administration");
        selectTab(0);
        setSize((bpan.getOffsetWidth() - 50) + "px", (bpan.getOffsetHeight() - 100) + "px");
        setStyleName("tabPanel");
    }

    public void adjustSize(int height) {
        bpan.adjustSize(height - 100);
        wpan.adjustSize(height - 150);
        apan.adjustSize(height - 150);
    }
}