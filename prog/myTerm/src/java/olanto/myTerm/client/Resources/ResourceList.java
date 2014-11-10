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
package olanto.myTerm.client.Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import olanto.myTerm.client.CookiesManager.MyTermCookies;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 *
 * @author nizar ghoula - simple
 */
public class ResourceList extends ListBox {

    private static AsyncCallback<ArrayList<Resource>> RsrcCallback;

    public ResourceList(long ownerID) {
        RsrcCallback = new AsyncCallback<ArrayList<Resource>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get list of resources");
            }

            @Override
            public void onSuccess(ArrayList<Resource> result) {
                int i = 0;
                for (Resource s : result) {
                    addItem(s.name, s.id);
                    if (s.name.equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.Resource))) {
                        i = result.indexOf(s);
                    }
                }
                setSelectedIndex(i);
            }
        };
        this.addChangeHandler(
                new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                MyTermCookies.updateCookie(MyTermCookiesNamespace.Resource, getItemText(getSelectedIndex()));
            }
        });
        addItem("ALL", "-1");
        getService().getResources(ownerID, RsrcCallback);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }
}
