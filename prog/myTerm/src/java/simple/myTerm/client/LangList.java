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
package simple.myTerm.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;

/**
 *
 * @author nizar ghoula - simple
 */
public class LangList extends ListBox {

    private static AsyncCallback<ArrayList<String>> langCallback;
    private String type;
    private ArrayList<String> langs;

    public LangList(String tp) {
        initCookies();
        this.type = tp;
        langCallback = new AsyncCallback<ArrayList<String>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get list of languages");
            }

            @Override
            public void onSuccess(ArrayList<String> result) {
                langs = result;
                for (String s : result) {
                    addItem(s);
                }
            }
        };
        getService().getLanguages(langCallback);
        if (type.equalsIgnoreCase("source")) {
            setSelectedIndex(getlangIndex(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangS)));
        } else {
            setSelectedIndex(getlangIndex(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangT)));
        }

        addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (type.equals("source")) {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangS, getItemText(getSelectedIndex()));
                } else {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangT, getItemText(getSelectedIndex()));
                }
            }
        });
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void initCookies() {
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangS, "EN");
        MyTermCookies.initCookie(MyTermCookiesNamespace.MyTermlangT, "FR");
    }

    private int getlangIndex(String cookie) {
        return langs.indexOf(cookie);
    }
}
