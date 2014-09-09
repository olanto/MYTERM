/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Main.ValidatorWidget;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author simple
 */
public class ApproverInterfaceWidget extends VerticalPanel {

    private ExpSearchHeaderMonoLingual searchMenu = new ExpSearchHeaderMonoLingual();
    private ExpSearchResultsContainer resultsPanel = new ExpSearchResultsContainer();

    public ApproverInterfaceWidget() {
        add(searchMenu);
        add(resultsPanel);
    }
}
