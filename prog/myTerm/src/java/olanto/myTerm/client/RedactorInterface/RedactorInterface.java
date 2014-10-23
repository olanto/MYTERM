/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.RedactorInterface;

import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.EditorWidget.WorkspaceWidget;
import olanto.myTerm.client.ValidatorWidget.ApproveWidget;
import olanto.myTerm.client.PublicWidget.MyTermSearchWidget;

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
}
