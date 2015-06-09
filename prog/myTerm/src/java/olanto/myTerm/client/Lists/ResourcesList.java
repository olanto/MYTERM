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
import olanto.myTerm.shared.ResourceDTO;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import java.util.Collection;
import olanto.myTerm.client.CookiesManager.MyTermCookies;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 *
 * @author nizar ghoula - simple
 */
public class ResourcesList extends ListBox {

    private final myTermServiceAsync rsrcService = GWT.create(myTermService.class);
    public AsyncCallback<Collection<ResourceDTO>> RsrcCallback;
    private ArrayList<String> rsrclist = new ArrayList<>();
    private ArrayList<Long> rsrcIDlist = new ArrayList<>();

    public ResourcesList(String ownerMailing, final String role) {
        super();
        RsrcCallback = new AsyncCallback<Collection<ResourceDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of resources, please RELOAD THE PAGE");
            }

            @Override
            public void onSuccess(Collection<ResourceDTO> result) {
                int i = 0;
                if (result != null) {
                    if (role.equals("READER")) {
                        addItem("ALL", "-1");
                        rsrclist.add("ALL");
                        rsrcIDlist.add(0L);
                        i++;
                    }
                    ArrayList<ResourceDTO> res = new ArrayList<>();
                    if (res.addAll(result)) {
                        for (ResourceDTO s : res) {
                            rsrclist.add(s.getResourceName());
                            rsrcIDlist.add(s.getIdResource());
                            addItem(s.getResourceName(), s.getIdResource().toString());
                            if (s.getResourceName().equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.Resource))) {
                                i = res.indexOf(s);
                            }
                        }

                        setSelectedIndex(i);
                    }
                }
            }
        };
        this.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                MyTermCookies.updateCookie(MyTermCookiesNamespace.Resource, getItemText(getSelectedIndex()));
            }
        });
        rsrcService.getResourcesByOwner(ownerMailing, role, RsrcCallback);
    }

    public ResourcesList() {
        super();
        RsrcCallback = new AsyncCallback<Collection<ResourceDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of resources, please RELOAD THE PAGE");
            }

            @Override
            public void onSuccess(Collection<ResourceDTO> result) {
                if (result != null) {
                    addItem(" ", "-1");
                    ArrayList<ResourceDTO> res = new ArrayList<>();
                    if (res.addAll(result)) {
                        for (ResourceDTO s : res) {
                            rsrclist.add(s.getResourceName());
                            rsrcIDlist.add(s.getIdResource());
                            addItem(s.getResourceName(), s.getIdResource().toString());
                        }
                        setSelectedIndex(0);
                    }
                }
            }
        };
        rsrcService.getResources(RsrcCallback);
    }

    public Long getIDResource(int i) {
        return rsrcIDlist.get(i);
    }

    public String getResName(Long IDRes) {
        int i = 0;
        for (Long s : rsrcIDlist) {
            if (s.equals(IDRes)) {
                return rsrclist.get(i);
            }
            i++;
        }
        return "";
    }

    public ArrayList<Long> getSelectedRsIDs(int idx) {
        ArrayList<Long> rsIDs = new ArrayList<>();
        if (idx == 0) {
            return rsrcIDlist;
        } else {
            rsIDs.add(rsrcIDlist.get(idx));
        }
        return rsIDs;
    }

    public ArrayList<Long> getResourcesIDs() {
        return rsrcIDlist;
    }
}
