/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.REVISORWidget;
import olanto.myTerm.client.Widgets.READERWidget;

/**
 *
 * @author simple
 */
public class RevisorInterface extends TabPanel {

    private READERWidget bpan;
    private REVISORWidget apan;

    public RevisorInterface(long ownerID) {
        bpan = new READERWidget(ownerID);
        apan = new REVISORWidget(ownerID);
        add(bpan, "Term Search");
        add(apan, "To approve");
        setStyleName("tabPanel");
        selectTab(0);
    }
}
