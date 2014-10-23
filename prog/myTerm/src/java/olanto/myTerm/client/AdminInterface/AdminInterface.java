/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.AdminInterface;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.EditorWidget.WorkspaceWidget;
import olanto.myTerm.client.ValidatorWidget.ApproveWidget;
import olanto.myTerm.client.PublicWidget.MyTermSearchWidget;

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