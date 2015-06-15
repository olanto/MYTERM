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

import olanto.myTerm.shared.LanguageDTO;
import olanto.myTerm.client.CookiesManager.MyTermCookies;
import olanto.myTerm.client.CookiesManager.MyTermCookiesNamespace;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import java.util.ArrayList;
import java.util.Collection;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;

/**
 *
 * @author nizar ghoula - simple
 */
public class LangList extends ListBox {

    private myTermServiceAsync langService = GWT.create(myTermService.class);
    private AsyncCallback<Collection<LanguageDTO>> langCallback;
    private ArrayList<String> langlist = new ArrayList<>();
    private ArrayList<String> langIDlist = new ArrayList<>();

    public LangList() {
        super();
        langCallback = new AsyncCallback<Collection<LanguageDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of languages, please RELOAD THE PAGE");
            }

            @Override
            public void onSuccess(Collection<LanguageDTO> result) {
                addItem(" ", " ");
                langlist.add(" ");
                langIDlist.add(" ");
                ArrayList<LanguageDTO> res = new ArrayList<>();
                if (res.addAll(result)) {
                    for (LanguageDTO s : res) {
                        langlist.add(s.getLanguageDefaultName());
                        langIDlist.add(s.getIdLanguage());
                        addItem(s.getLanguageDefaultName(), s.getIdLanguage());
                    }
                    setSelectedIndex(0);
                }
            }
        };
        langService.getLanguages(langCallback);
    }

    public LangList(final String type) {
        super();
        langCallback = new AsyncCallback<Collection<LanguageDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of languages, please RELOAD THE PAGE");
            }

            @Override
            public void onSuccess(Collection<LanguageDTO> result) {
                ArrayList<LanguageDTO> res = new ArrayList<>();
                if (res.addAll(result)) {
                    int i = 0, j = 0;
                    for (LanguageDTO s : res) {
                        langlist.add(s.getLanguageDefaultName());
                        langIDlist.add(s.getIdLanguage());
                        addItem(s.getLanguageDefaultName(), s.getIdLanguage());
                        if (s.getLanguageDefaultName().equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangS))) {
                            i = res.indexOf(s);
                        }
                        if (s.getLanguageDefaultName().equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangT))) {
                            j = res.indexOf(s);
                        }
                    }
                    if (type.equals("source")) {
                        setSelectedIndex(i);
                    } else {
                        setSelectedIndex(j);
                    }
                }
            }
        };
        this.addChangeHandler(
                new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (type.equals("source")) {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangS, getItemText(getSelectedIndex()));
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermIDlangS, getItemText(getSelectedIndex()));
                } else {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangT, getItemText(getSelectedIndex()));
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermIDlangT, getItemText(getSelectedIndex()));
                }
            }
        });
        langService.getLanguages(langCallback);
    }

    public LangList(long ownerID, final String type) {
        super();
        langCallback = new AsyncCallback<Collection<LanguageDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of languages");
            }

            @Override
            public void onSuccess(Collection<LanguageDTO> result) {
                ArrayList<LanguageDTO> res = new ArrayList<>();
                if (res.addAll(result)) {
                    int i = 0, j = 0;
                    for (LanguageDTO s : res) {
                        langlist.add(s.getLanguageDefaultName());
                        langIDlist.add(s.getIdLanguage());
                        addItem(s.getLanguageDefaultName(), s.getIdLanguage());
                        if (s.getLanguageDefaultName().equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangSrc))) {
                            i = res.indexOf(s);
                        }
                        if (s.getLanguageDefaultName().equalsIgnoreCase(Cookies.getCookie(MyTermCookiesNamespace.MyTermlangTgt))) {
                            j = res.indexOf(s);
                        }
                    }
                    if (type.equals("source")) {
                        setSelectedIndex(i);
                    } else {
                        setSelectedIndex(j);
                    }
                }
            }
        };
        this.addChangeHandler(
                new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                if (type.equals("source")) {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangSrc, getItemText(getSelectedIndex()));
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermIDlangSrc, getValue(getSelectedIndex()));
                } else {
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermlangTgt, getItemText(getSelectedIndex()));
                    MyTermCookies.updateCookie(MyTermCookiesNamespace.MyTermIDlangTgt, getValue(getSelectedIndex()));
                }
            }
        });
        langService.getLanguagesByOwner(ownerID, langCallback);
    }

    public LangList(long ownerID, final BooleanWrap isEdited) {
        super();
        langCallback = new AsyncCallback<Collection<LanguageDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of languages");
            }

            @Override
            public void onSuccess(Collection<LanguageDTO> result) {
                for (LanguageDTO s : result) {
                    langlist.add(s.getLanguageDefaultName());
                    langIDlist.add(s.getIdLanguage());
                    addItem(s.getLanguageDefaultName(), s.getIdLanguage());
                }
                setSelectedIndex(0);
            }
        };
        this.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                isEdited.setVal(true);
            }
        });
        langService.getLanguagesByOwner(ownerID, langCallback);
    }

    public LangList(long ownerID, final BooleanWrap isEdited, final BooleanWrap isLocallyEdited) {
        super();
        langCallback = new AsyncCallback<Collection<LanguageDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of languages");
            }

            @Override
            public void onSuccess(Collection<LanguageDTO> result) {
                for (LanguageDTO s : result) {
                    langlist.add(s.getLanguageDefaultName());
                    langIDlist.add(s.getIdLanguage());
                    addItem(s.getLanguageDefaultName(), s.getIdLanguage());
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
        langService.getLanguagesByOwner(ownerID, langCallback);
    }

    public LangList(final String currentlangID, final BooleanWrap isEdited, final BooleanWrap isLocallyEdited) {
        super();
        langCallback = new AsyncCallback<Collection<LanguageDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of languages");
            }

            @Override
            public void onSuccess(Collection<LanguageDTO> result) {
                ArrayList<LanguageDTO> res = new ArrayList<>();
                if (res.addAll(result)) {
                    int i = 0;
                    for (LanguageDTO s : res) {
                        addItem(s.getLanguageDefaultName(), s.getIdLanguage());
                        if (s.getIdLanguage().equalsIgnoreCase(currentlangID)) {
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
        langService.getLanguages(langCallback);
    }

    public LangList(final BooleanWrap isEdited, final BooleanWrap isLocallyEdited) {
        super();
        langCallback = new AsyncCallback<Collection<LanguageDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of languages");
            }

            @Override
            public void onSuccess(Collection<LanguageDTO> result) {
                for (LanguageDTO s : result) {
                    addItem(s.getLanguageDefaultName(), s.getIdLanguage());
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
        langService.getLanguages(langCallback);
    }

    public void selectlanguage(String language) {
        int i = 0;
        for (String s : langlist) {
            if (s.equalsIgnoreCase(language)) {
                this.setSelectedIndex(i);
                break;
            }
            i++;
        }
    }

    public void setSelectedIndexByLangID(String IDlang) {
        int i = 0;
        for (String s : langIDlist) {
            if (s.equalsIgnoreCase(IDlang)) {
                this.setSelectedIndex(i);
                break;
            }
            i++;
        }
    }

    public ArrayList<String> getLangIDs() {
        return langIDlist;
    }
}
