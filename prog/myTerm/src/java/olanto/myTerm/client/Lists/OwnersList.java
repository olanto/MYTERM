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
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.OwnerDTO;

/**
 *
 * @author nizar ghoula - simple
 */
public class OwnersList extends ListBox {

    private final myTermServiceAsync ownerService = GWT.create(myTermService.class);
    public AsyncCallback<Collection<OwnerDTO>> ownerCallback;
    private ArrayList<String> ownerlist = new ArrayList<>();
    private ArrayList<Long> ownerIDlist = new ArrayList<>();

    public OwnersList() {
        super();
        ownerCallback = new AsyncCallback<Collection<OwnerDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of owners, please RELOAD THE PAGE");
            }

            @Override
            public void onSuccess(Collection<OwnerDTO> result) {
                addItem(" ", "-1");
                ownerlist.add(" ");
                ownerIDlist.add(-1l);
                if (result != null) {
                    ArrayList<OwnerDTO> res = new ArrayList<>();
                    if (res.addAll(result)) {
                        for (OwnerDTO s : res) {
                            ownerlist.add(s.getEmail());
                            ownerIDlist.add(s.getId());
                           addItem(s.getLastName(), "" + s.getId());
                            setTitle(s.getEmail());
                        }
                    }
                    setSelectedIndex(0);
                }
            }
        };
        ownerService.getOwners(ownerCallback);
    }

    public OwnersList(final BooleanWrap isEdited, final BooleanWrap isLocallyEdited) {
        super();
        ownerCallback = new AsyncCallback<Collection<OwnerDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of owners, please RELOAD THE PAGE");
            }

            @Override
            public void onSuccess(Collection<OwnerDTO> result) {
                addItem(" ", "-1");
                ownerlist.add(" ");
                ownerIDlist.add(-1l);
                if (result != null) {
                    ArrayList<OwnerDTO> res = new ArrayList<>();
                    if (res.addAll(result)) {
                        for (OwnerDTO s : res) {
                            ownerlist.add(s.getEmail());
                            ownerIDlist.add(s.getId());
                            addItem(s.getLastName(), "" + s.getId());
                            setTitle(s.getEmail());
                        }
                    }
                    setSelectedIndex(0);
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
        ownerService.getOwners(ownerCallback);
    }

    public OwnersList(final Long currentOwner, final BooleanWrap isEdited, final BooleanWrap isLocallyEdited) {
        super();
        ownerCallback = new AsyncCallback<Collection<OwnerDTO>>() {
            @Override
            public void onFailure(Throwable caught) {
                MainEntryPoint.statusPanel.setMessage("warning", "Failed to get list of owners, please RELOAD THE PAGE");
            }

            @Override
            public void onSuccess(Collection<OwnerDTO> result) {
                if (result != null) {
                    int i = 0;
                    ArrayList<OwnerDTO> res = new ArrayList<>();
                    if (res.addAll(result)) {
                        for (OwnerDTO s : res) {
                            ownerlist.add(s.getEmail());
                            ownerIDlist.add(s.getId());
                            addItem(s.getLastName(), "" + s.getId());
                            setTitle(s.getEmail());
                            if (currentOwner.compareTo(s.getId()) == 0) {
                                i = res.indexOf(s);
                            }
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
        ownerService.getOwners(ownerCallback);
    }
}
