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
package olanto.myTerm.client.Lists;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 *
 * @author nizar ghoula - simple
 */
public class TermGenderList extends ListBox {

    private myTermServiceAsync genderService = GWT.create(myTermService.class);
    private AsyncCallback<ArrayList<String>> genderCallback;

    public TermGenderList(String langID, final String currentType) {
        super();
        genderCallback = new AsyncCallback<ArrayList<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of term gender");
            }

            @Override
            public void onSuccess(ArrayList<String> result) {
                int i = 0;
                for (String s : result) {
                    addItem(s, s);
                    if (s.equalsIgnoreCase(currentType)) {
                        i = result.indexOf(s);
                    }
                }
                setSelectedIndex(i);
            }
        };
        genderService.getTermGender(langID, genderCallback);
    }
    
     public TermGenderList(String langID) {
        super();
        genderCallback = new AsyncCallback<ArrayList<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of term gender");
            }

            @Override
            public void onSuccess(ArrayList<String> result) {
                for (String s : result) {
                    addItem(s, s);
                }
                setSelectedIndex(0);
            }
        };
        genderService.getTermGender(langID, genderCallback);
    }
}
