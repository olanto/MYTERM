/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.myTerm.client.Main.EditorWidget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import simple.myTerm.client.Login.requests.LoginService;
import simple.myTerm.client.Login.requests.LoginServiceAsync;
import simple.myTerm.client.Main.request.myTermService;
import simple.myTerm.client.Main.request.myTermServiceAsync;
import simple.myTerm.client.Main.searchWidget.SearchEditResultsContainer;
import simple.myTerm.client.Main.searchWidget.SearchHeaderBasic;
import simple.myTerm.client.Main.searchWidget.SearchHeaderMonLingual;

/**
 *
 * @author simple
 */
public class EditorInterfaceWidget extends VerticalPanel {

    private SearchHeaderMonLingual searchMenu = new SearchHeaderMonLingual();
    private SearchEditResultsContainer resultsPanel = new SearchEditResultsContainer();

    public EditorInterfaceWidget() {
        add(searchMenu);
        add(resultsPanel);
    }
}
