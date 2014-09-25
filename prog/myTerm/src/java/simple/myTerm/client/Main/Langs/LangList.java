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
package simple.myTerm.client.Main.Langs;

import simple.myTerm.client.Main.cookiesManager.MyTermCookies;
import simple.myTerm.client.Main.cookiesManager.MyTermCookiesNamespace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import simple.myTerm.client.Main.request.myTermService;
import simple.myTerm.client.Main.request.myTermServiceAsync;

/**
 *
 * @author nizar ghoula - simple
 */
public class LangList extends ListBox {

    private static AsyncCallback<ArrayList<Language>> langCallback;

    public LangList(final String type) {
        langCallback = new AsyncCallback<ArrayList<Language>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get list of languages");
            }

            @Override
            public void onSuccess(ArrayList<Language> result) {
                int i = 0, j = 0;
                for (Language s : result) {
                    addItem(s.language, s.id);
                    if (s.language.equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangS))) {
                        i = result.indexOf(s);
                    }
                    if (s.language.equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangT))) {
                        j = result.indexOf(s);
                    }
                }
                if (type.equals("source")) {
                    setSelectedIndex(i);
                } else {
                    setSelectedIndex(j);
                }

            }
        };
        this.addChangeHandler(
                new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (type.equals("source")) {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangS, getItemText(getSelectedIndex()));
                } else {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangT, getItemText(getSelectedIndex()));
                }
            }
        });
        getService().getLanguages(langCallback);
    }

    public LangList() {
        langCallback = new AsyncCallback<ArrayList<Language>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Failed to get list of languages");
            }

            @Override
            public void onSuccess(ArrayList<Language> result) {
                for (Language s : result) {
                    addItem(s.language, s.id);
                }
            }
        };
        getService().getLanguages(langCallback);
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }
}
