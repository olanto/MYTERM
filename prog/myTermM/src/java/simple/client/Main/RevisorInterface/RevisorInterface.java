/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.client.Main.RevisorInterface;

import com.google.gwt.user.client.ui.TabPanel;
import simple.client.Main.EditorWidget.WorkspaceWidget;
import simple.client.Main.ValidatorWidget.ApproveWidget;
import simple.client.Main.publicWidget.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class RevisorInterface extends TabPanel {

    private MyTermSearchWidget bpan = new MyTermSearchWidget();
    private WorkspaceWidget wpan = new WorkspaceWidget();
    private ApproveWidget apan = new ApproveWidget();

    public RevisorInterface() {
        add(bpan, "Browse your Resources");
        add(apan, "To approve");
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
