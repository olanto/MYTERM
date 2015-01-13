/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.READERWidget;

/**
 *
 * @author simple
 */
public class ReaderInterface extends TabPanel {

    private READERWidget bpan;

    public ReaderInterface(long ownerID) {
        bpan = new READERWidget(ownerID);
        add(bpan, "Term Search");
        setStyleName("tabPanel");
        selectTab(0);
    }
}
