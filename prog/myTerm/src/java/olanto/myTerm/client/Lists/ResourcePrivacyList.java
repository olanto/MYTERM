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
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 *
 * @author nizar ghoula - simple
 */
public class ResourcePrivacyList extends ListBox {

    private myTermServiceAsync rsrcPrivacyService = GWT.create(myTermService.class);
    private AsyncCallback<Map<String, String>> rsrcPrivacyCallback;

    public ResourcePrivacyList(String langID) {
        super();
        rsrcPrivacyCallback = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get values of resources privacy");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                addItem(" ", " ");
                for (String s : result.keySet()) {
                    addItem(s, result.get(s));
                }
                setSelectedIndex(0);
            }
        };
        rsrcPrivacyService.getResourcePrivacy(langID, rsrcPrivacyCallback);
    }

    public ResourcePrivacyList(String langID, final String currentPV, final BooleanWrap isEdited, final BooleanWrap isLocallyEdited) {
        super();
        rsrcPrivacyCallback = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get values of resources privacy");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                ArrayList<String> res = new ArrayList<>();
                if (res.addAll(result.keySet())) {
                    int i = 0;
                    for (String s : res) {
                        addItem(s, result.get(s));
                        if (result.get(s).equalsIgnoreCase(currentPV)) {
                            i = res.indexOf(s);
                        }
                    }
                    setSelectedIndex(i);
                }
            }
        };
        this.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                isEdited.setVal(true);
                isLocallyEdited.setVal(true);
            }
        });
        rsrcPrivacyService.getResourcePrivacy(langID, rsrcPrivacyCallback);
    }

    public ResourcePrivacyList(String langID, final BooleanWrap isEdited, final BooleanWrap isLocallyEdited) {
        super();
        rsrcPrivacyCallback = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get values of resources privacy");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                for (String s : result.keySet()) {
                    addItem(s, result.get(s));
                }
                setSelectedIndex(0);
            }
        };
        this.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                isEdited.setVal(true);
                isLocallyEdited.setVal(true);
            }
        });
        rsrcPrivacyService.getResourcePrivacy(langID, rsrcPrivacyCallback);
    }
}
