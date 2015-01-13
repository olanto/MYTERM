/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.REDACTORWidget;
import olanto.myTerm.client.Widgets.READERWidget;

/**
 *
 * @author simple
 */
public class RedactorInterface extends TabPanel {

    private READERWidget bpan;
    private REDACTORWidget wpan;

    public RedactorInterface(long ownerID) {
        bpan = new READERWidget(ownerID);
        wpan = new REDACTORWidget(ownerID);
        add(bpan, "Term Search");
        add(wpan, "Workspace");
        setStyleName("tabPanel");
        selectTab(0);
    }
}
