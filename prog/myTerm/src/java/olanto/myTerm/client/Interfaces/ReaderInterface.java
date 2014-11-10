/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class ReaderInterface extends TabPanel {

    private MyTermSearchWidget bpan;

    public ReaderInterface(long ownerID) {
        bpan = new MyTermSearchWidget(ownerID);
        add(bpan, "Term Search");
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
