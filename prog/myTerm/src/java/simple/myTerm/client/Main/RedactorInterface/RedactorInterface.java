/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Main.RedactorInterface;

import com.google.gwt.user.client.ui.TabPanel;
import simple.myTerm.client.Main.EditorWidget.WorkspaceWidget;
import simple.myTerm.client.Main.ValidatorWidget.ApproveWidget;
import simple.myTerm.client.Main.publicWidget.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class RedactorInterface extends TabPanel {

    private MyTermSearchWidget bpan = new MyTermSearchWidget();
    private WorkspaceWidget wpan = new WorkspaceWidget();
    private ApproveWidget apan = new ApproveWidget();

    public RedactorInterface() {
        add(bpan, "Browse your Resources");
        add(wpan, "Workspace");
        add(apan, "To approve");
        selectTab(0);
        setStyleName("tabPanel");
    }

    public void adjustSize(int h) {
        bpan.adjustSize(getOffsetHeight());
        wpan.adjustSize(getOffsetHeight());
        apan.adjustSize(getOffsetHeight());
    }
}
