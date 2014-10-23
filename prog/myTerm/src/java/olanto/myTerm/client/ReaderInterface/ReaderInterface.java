/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package olanto.myTerm.client.ReaderInterface;

import com.google.gwt.user.client.ui.TabPanel;
import olanto.myTerm.client.PublicWidget.MyTermSearchWidget;

/**
 *
 * @author simple
 */
public class ReaderInterface extends TabPanel {

    private MyTermSearchWidget bpan = new MyTermSearchWidget();

    public ReaderInterface() {
        add(bpan, "Browse your Resources");
        setStyleName("tabPanel");
    }
}
