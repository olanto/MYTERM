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
import java.util.Map;
import java.util.logging.Level;
import olanto.myTerm.client.MainEntryPoint;
import static olanto.myTerm.client.MainEntryPoint.logger;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author nizar ghoula - simple
 */
public class TermTypeList extends ListBox {

    private myTermServiceAsync typesService = GWT.create(myTermService.class);
    private AsyncCallback<Map<String, String>> termTypesCallback;

    public TermTypeList(String langID, final String currentType, final BooleanWrap isEdited, SysFieldDTO type) {
        super();
        termTypesCallback = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of term types");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                ArrayList<String> res = new ArrayList<>();
                  for (int j = 0; j < result.size(); j++) {
                    String posj = j + "";
                    String val = result.get(posj);
                    res.add(val);
                    logger.log(Level.SEVERE, "TermTypeList1 add:" + result.get(posj) + ", index:" + posj);
                }
                if (res.size() > 0) {
                    int i = 0;
                    for (int j = 0; j < result.size(); j++) {
                        String posj = j + "";
                        String val = result.get(posj);
                        addItem(val, val);
                        if (val.equalsIgnoreCase(currentType)) {
                            i = j;
                        }
                        logger.log(Level.SEVERE, "TermTypeList1 find idx s:" + val + ", index:" + i);
                    }    
                
//                if (res.addAll(result.keySet())) {
//                    int i = 0;
//                    for (String s : res) {
//                        System.out.println();
//                        addItem(s, result.get(s));
//                        if (result.get(s).equalsIgnoreCase(currentType)) {
//                            i = res.indexOf(s);
//                        }
//                        logger.log(Level.SEVERE, "TermTypeList1 s:" + s + ", index:" + i);
//                    }
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
        this.setVisible(type.getVisibilityForm());
        typesService.getTermTypes(langID, termTypesCallback);
    }

    public TermTypeList(String langID, final BooleanWrap isEdited, SysFieldDTO type) {
        super();
        termTypesCallback = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of term types");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                             logger.log(Level.SEVERE, "TermTypeList2 size:" + result.size());
                for (int j = 0; j < result.size(); j++) {
                    String posj = j + "";
                    String val = result.get(posj);
                    addItem(val, val);
                    logger.log(Level.SEVERE, "TermTypeList2 s:" + val + ", index:" + j + "first 0");
                   }
//                for (String s : result.keySet()) {
//                    addItem(s, s);
//                    logger.log(Level.SEVERE, "TermTypeList2 s:" + s + ", index:" + 0);
//                }
                setSelectedIndex(0);
            }
        };
        this.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                isEdited.setVal(true);
            }
        });
        this.setVisible(type.getVisibilityForm());
        typesService.getTermTypes(langID, termTypesCallback);
    }

    public TermTypeList(String langID, final String currentType, final BooleanWrap isEdited, final BooleanWrap isLocallyEdited, SysFieldDTO type) {
        super();
        termTypesCallback = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of term types");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                logger.log(Level.SEVERE, "TermTypeList3 size:" + result.size());
                ArrayList<String> res = new ArrayList<>();
                for (int j = 0; j < result.size(); j++) {
                    String posj = j + "";
                    String val = result.get(posj);
                    res.add(val);
                    logger.log(Level.SEVERE, "TermTypeList3 add:" + result.get(posj) + ", index:" + posj);
                }
                if (res.size() > 0) {
                    int i = 0;
                    for (int j = 0; j < result.size(); j++) {
                        String posj = j + "";
                        String val = result.get(posj);
                        addItem(val, val);
                        if (val.equalsIgnoreCase(currentType)) {
                            i = j;
                        }
                        logger.log(Level.SEVERE, "TermTypeList3 find idx s:" + val + ", index:" + i);
                    }
//                    for (String s : res) {
//                        addItem(s, result.get(s));
//                        if (result.get(s).equalsIgnoreCase(currentType)) {
//                            i = res.indexOf(s);
//                        }
//                        logger.log(Level.SEVERE, "TermTypeList3 find idx s:" + s + ", index:" + i);
//                    }
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
        this.setVisible(type.getVisibilityForm());
        typesService.getTermTypes(langID, termTypesCallback);
    }

    public TermTypeList(String langID, final BooleanWrap isEdited, final BooleanWrap isLocallyEdited, SysFieldDTO type) {
        super();
        termTypesCallback = new AsyncCallback<Map<String, String>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of term types");
            }

            @Override
            public void onSuccess(Map<String, String> result) {
                logger.log(Level.SEVERE, "TermTypeList4 size:" + result.size());
                for (int j = 0; j < result.size(); j++) {
                    String posj = j + "";
                    String val = result.get(posj);
                    addItem(val, val);
                    logger.log(Level.SEVERE, "TermTypeList4 s:" + val + ", index:" + j + "first 0");
                }
//                for (String s : result.keySet()) {
//                    addItem(s, s);
//                    logger.log(Level.SEVERE, "TermTypeList4 s:" + s + ", index:" + 0);
//                }
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
        this.setVisible(type.getVisibilityForm());
        typesService.getTermTypes(langID, termTypesCallback);
    }
}
