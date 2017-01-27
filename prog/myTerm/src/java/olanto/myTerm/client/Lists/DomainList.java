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

import olanto.myTerm.shared.DomainDTO;
import olanto.myTerm.client.CookiesManager.MyTermCookies;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import java.util.Collection;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 *
 * @author nizar ghoula - simple
 */
public class DomainList extends ListBox {

    private final myTermServiceAsync domService = GWT.create(myTermService.class);
    private AsyncCallback<Collection<DomainDTO>> domCallback;

    public DomainList(String all) {
        super();
        domCallback = new AsyncCallback<Collection<DomainDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get list of domains");
            }

            @Override
            public void onSuccess(Collection<DomainDTO> result) {
                ArrayList<DomainDTO> res = new ArrayList<>();
                if (res.addAll(result)) {
                    int i = 0;
                    for (DomainDTO s : res) {
                        addItem(s.getDomainDefaultName(), s.getDomainDefaultName());
                        if (s.getDomainDefaultName().equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.Domain))) {
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
                MyTermCookies.updateCookie(MyTermCookiesNamespace.Domain, getItemText(getSelectedIndex()));
            }
        });
        addItem(all, " ");
        domService.getDomains(domCallback);
    }

    public DomainList(final BooleanWrap isEdited, String all) {
        super();
        domCallback = new AsyncCallback<Collection<DomainDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get list of domains");
            }

            @Override
            public void onSuccess(Collection<DomainDTO> result) {
                ArrayList<DomainDTO> res = new ArrayList<>();
                if (res.addAll(result)) {
                    int i = 0;
                    for (DomainDTO s : res) {
                        addItem(s.getDomainDefaultName(), s.getDomainDefaultName());
                        if (s.getDomainDefaultName().equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.Domain))) {
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
            }
        });
        addItem(all, " ");
        domService.getDomains(domCallback);
    }

    public DomainList(final BooleanWrap isEdited, final String currentDomain, final String all) {

        domCallback = new AsyncCallback<Collection<DomainDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get list of domains");
            }

            @Override
            public void onSuccess(Collection<DomainDTO> result) {
                addItem(all, " ");
                ArrayList<DomainDTO> res = new ArrayList<>();
                if (res.addAll(result)) {
                    int i = 1;
                    for (DomainDTO s : res) {
                        addItem(s.getDomainDefaultName(), s.getDomainDefaultName());
                        if (s.getDomainDefaultName().equalsIgnoreCase(currentDomain)) {
                            i = res.indexOf(s);
                        }
                    }
                    setSelectedIndex(i + 1);
                }
            }
        };
        this.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                isEdited.setVal(true);
            }
        });
        domService.getDomains(domCallback);
    }
}
