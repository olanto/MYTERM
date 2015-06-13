/**
 * ********
 * Copyright © 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myTERM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myTERM. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package olanto.myTerm.client.Widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import olanto.myTerm.client.ContainerPanels.ResultsContainerADMIN;
import olanto.myTerm.client.ContainerPanels.SearchHeaderUSER_LANGUAGE;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author simple
 */
public class USERS_LANGUAGESWidget extends VerticalPanel {

    private static SearchHeaderUSER_LANGUAGE searchMenu;
    private static ResultsContainerADMIN resultsPanel;
    private HashMap<String, SysFieldDTO> sFields;
    private HashMap<String, String> sysMsgs;
    public BooleanWrap isEdited = new BooleanWrap();
    private static AsyncCallback<String> usersLanguagesCallback;

    public USERS_LANGUAGESWidget(HashMap<String, SysFieldDTO> sysFields, HashMap<String, String> sysMsg) {
        sFields = sysFields;
        sysMsgs = sysMsg;
        resultsPanel = new ResultsContainerADMIN();
        fixGwtNav();
        searchMenu = new SearchHeaderUSER_LANGUAGE(sysMsg);
        add(searchMenu);
        add(resultsPanel);
        usersLanguagesCallback = new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                History.newItem("page34");
            }

            @Override
            public void onSuccess(String result) {
                MainEntryPoint.statusPanel.setMessage("message", "Relations retreived successfully...");
                searchMenu.btnAdd.setEnabled(true);
                if (result != null) {
                    resultsPanel.elementDetails.clear();
                    resultsPanel.sideRes.clear();
                    resultsPanel.sideRes.setWidget(new HTML(result));
                }
                History.newItem("p34loaded");
            }
        };
        History.addValueChangeHandler(new ValueChangeHandler<String>() {
            @Override
            public void onValueChange(ValueChangeEvent<String> event) {
                MainEntryPoint.statusPanel.clearMessages();
                String command = event.getValue();
                if (command.contains("ULM")) {
//                    if (isEdited.getVal()) {
//                        new MyDialog("You have edited this user. Are you sure that you want to abort all the modifications?", 1, command).show();
//                    } else {
//                        long resID = Long.parseLong(command.substring(2));
//                        getService().getResourceDetails(resID, getResourceDetailsCallback);
//                    }
                } else {
                    switch (command) {
                        case "page34":
                            commandInit();
                            break;
                    }
                }
            }
        });
        resultsPanel.adjustSize(0.4f, 0.6f);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void commandInit() {
        isEdited.setVal(false);
        resultsPanel.sideRes.clear();
        MainEntryPoint.statusPanel.setMessage("warning", "Retrieving entries, please wait...");
        getService().getUsersLanguagesDetails(-1, " ", usersLanguagesCallback);
    }

    public static native void fixGwtNav() /*-{
     $wnd.gwtnav = function(a) {
     var realhref = decodeURI(a.href.split("#")[1].split("?")[0]);
     @com.google.gwt.user.client.History::newItem(Ljava/lang/String;)(realhref);
     return false;
     }
     }-*/;
}
