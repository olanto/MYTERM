/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.WorkspaceWidget;
import olanto.myTerm.client.Widgets.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class RedactorInterface extends TabPanel {

    private MyTermSearchWidget bpan;
    private WorkspaceWidget wpan;

    public RedactorInterface(long ownerID) {
        bpan = new MyTermSearchWidget(ownerID);
        wpan = new WorkspaceWidget(ownerID);
        add(bpan, "Term Search");
        add(wpan, "Workspace");
        setStyleName("tabPanel");
        selectTab(0);
    }
}
