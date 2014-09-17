/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Main.ReaderInterface;

import com.google.gwt.user.client.ui.TabPanel;
import simple.myTerm.client.Main.EditorWidget.WorkspaceWidget;
import simple.myTerm.client.Main.ValidatorWidget.ApproveWidget;
import simple.myTerm.client.Main.publicWidget.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class ReaderInterface extends TabPanel {

    private MyTermSearchWidget bpan = new MyTermSearchWidget();
    private WorkspaceWidget wpan = new WorkspaceWidget();
    private ApproveWidget apan = new ApproveWidget();

    public ReaderInterface() {
        add(bpan, "Browse your Resources");
        setSize((bpan.getOffsetWidth() - 50) + "px", (bpan.getOffsetHeight() - 100) + "px");
        setStyleName("tabPanel");
    }

    public void adjustSize(int height) {
        bpan.adjustSize(height - 100);
        wpan.adjustSize(height - 150);
        apan.adjustSize(height - 150);
    }
}
