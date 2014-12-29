/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

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
        setStyleName("tabPanel");
        selectTab(0);
    }
}
