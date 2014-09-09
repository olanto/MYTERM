/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Main.EditorWidget;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author simple
 */
public class EditorInterfaceWidget extends VerticalPanel {

    private SearchHeaderMonoLingual searchMenu = new SearchHeaderMonoLingual();
    private SearchEditResultsContainer resultsPanel = new SearchEditResultsContainer();

    public EditorInterfaceWidget() {
        add(searchMenu);
        add(resultsPanel);
    }
}
