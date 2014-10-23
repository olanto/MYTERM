/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Main.RevisorInterface;

import com.google.gwt.user.client.ui.TabPanel;
import simple.myTerm.client.Main.ValidatorWidget.ApproveWidget;
import simple.myTerm.client.Main.publicWidget.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class RevisorInterface extends TabPanel {

    private MyTermSearchWidget bpan = new MyTermSearchWidget();
    private ApproveWidget apan = new ApproveWidget();

    public RevisorInterface() {
        add(bpan, "Browse your Resources");
        add(apan, "To approve");
        selectTab(0);
        setStyleName("tabPanel");
    }
   }
