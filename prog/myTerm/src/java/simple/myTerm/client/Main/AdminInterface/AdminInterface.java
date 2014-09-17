/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Main.AdminInterface;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import simple.myTerm.client.Main.EditorWidget.WorkspaceWidget;
import simple.myTerm.client.Main.ValidatorWidget.ApproveWidget;
import simple.myTerm.client.Main.publicWidget.MyTermSearchWidget;
import simple.myTerm.shared.UserDto;

/**
 *
 * @author simple
 */
public class AdminInterface extends VerticalPanel {

    private MyTermSearchWidget bpan = new MyTermSearchWidget();
//    private WorkspaceWidget wpan = new WorkspaceWidget();
//    private ApproveWidget apan = new ApproveWidget();
    TabLayoutPanel tpanel = new TabLayoutPanel(1.5, Unit.EM);
    public AdminInterface(UserDto user){
        add(bpan);
        tpanel.add(bpan, "Browse your Resources");
//        tpanel.add(wpan, "Workspace");
//        tpanel.add(apan, "To approve");
        tpanel.add(new HTML("Under construction!!!"), "System Administration");
    }
    public void adjustSize(int height) {
        bpan.adjustSize(height);
//        wpan.adjustSize(height);
//        apan.adjustSize(height);
    }
}
