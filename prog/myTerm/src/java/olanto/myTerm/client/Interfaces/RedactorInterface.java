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
import olanto.myTerm.client.Widgets.ApproveWidget;
import olanto.myTerm.client.Widgets.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class RedactorInterface extends TabPanel {

    private MyTermSearchWidget bpan;
    private WorkspaceWidget wpan;
    private ApproveWidget apan;

    public RedactorInterface(long ownerID) {
        bpan = new MyTermSearchWidget(ownerID);
        wpan = new WorkspaceWidget(ownerID);
        apan = new ApproveWidget(ownerID);
        add(bpan, "Term Search");
        add(wpan, "Workspace");
        add(apan, "To approve");
        selectTab(0);
        setStyleName("tabPanel");
        addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                History.newItem("page" + event.getSelectedItem());
            }
        });
    }
}
