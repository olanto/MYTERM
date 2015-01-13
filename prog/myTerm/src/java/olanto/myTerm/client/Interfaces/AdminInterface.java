/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.Interfaces;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.Widgets.REVISORWidget;
import olanto.myTerm.client.Widgets.REDACTORWidget;
import olanto.myTerm.client.Widgets.READERWidget;

/**
 *
 * @author simple
 */
public class AdminInterface extends TabPanel {

    private READERWidget bpan;
    private REDACTORWidget wpan;
    private REVISORWidget apan;

    public AdminInterface(long ownerID) {
        bpan = new READERWidget(ownerID);
        wpan = new REDACTORWidget(ownerID);
        apan = new REVISORWidget(ownerID);
        add(bpan, "Term Search");
        add(wpan, "Workspace");
        add(apan, "To approve");
        add(new HTML("Under construction!!!"), "System Administration");

        setStyleName("tabPanel");
        selectTab(0);
    }
}
