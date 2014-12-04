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

import olanto.myTerm.shared.ResourceDTO;
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

    private static AsyncCallback<ArrayList<ResourceDTO>> RsrcCallback;
    private static ArrayList<String> rsrclist = new ArrayList<>();
    private static ArrayList<Long> rsrcIDlist = new ArrayList<>();

    public ResourceList() {
        RsrcCallback = new AsyncCallback<ArrayList<ResourceDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get list of resources");
            }

            @Override
            public void onSuccess(ArrayList<ResourceDTO> result) {
                if (result != null) {
                    int i = 0;
                    for (ResourceDTO s : result) {
                        rsrclist.add(s.getResourceName());
                        rsrcIDlist.add(s.getIdResource());
                        addItem(s.getResourceName(), s.getIdResource().toString());
                        if (s.getResourceName().equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.Resource))) {
                            i = result.indexOf(s);
                        }
                    }
                    setSelectedIndex(i);
                }
            }
        };
        this.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                MyTermCookies.updateCookie(MyTermCookiesNamespace.Resource, getItemText(getSelectedIndex()));
            }
        });
        addItem("ALL", "-1");
        getService().getResources(Long.parseLong(Cookies.getCookie(MyTermCookiesNamespace.ownerID)), RsrcCallback);
    }

    public void selectResource(String resource) {
        int i = 0;
        for (String s : rsrclist) {
            if (s.equalsIgnoreCase(resource)) {
                setSelectedIndex(i);
                break;
            }
            i++;
        }
    }

    public void selectResourcebyID(long resourceID) {
        int i = 0;
        for (Long s : rsrcIDlist) {
            if (s.equals(resourceID)) {
                setSelectedIndex(i);
                break;
            }
            i++;
        }
    }

    public long getIDResource(int i) {
        return rsrcIDlist.get(i);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }
}
