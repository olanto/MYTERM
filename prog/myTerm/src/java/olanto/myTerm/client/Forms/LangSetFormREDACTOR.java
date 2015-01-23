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
package olanto.myTerm.client.Forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.HashMap;
import olanto.myTerm.client.MainEntryPoint;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.client.ServiceCalls.myTermService;
import olanto.myTerm.client.ServiceCalls.myTermServiceAsync;
import olanto.myTerm.shared.LangEntryDTO;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.TermDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class LangSetFormREDACTOR extends VerticalPanel {

    public VerticalPanel desc = new VerticalPanel();
    private HorizontalPanel controls = new HorizontalPanel();
    public Button addTerm = new Button("Add Term");
    private ArrayList<TermFormREDACTOR> terms;
    private ArrayList<TermFormREDACTOR> remterms;
    private long ownerID;

    public LangSetFormREDACTOR(long idOwner, final HashMap<String, SysFieldDTO> sFields, final BooleanWrap isEdited) {
        ownerID = idOwner;
        terms = new ArrayList<>();
        remterms = new ArrayList<>();
        this.setStyleName("langSetForm");
        add(desc);
        add(controls);
        controls.add(addTerm);
        addTerm.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final TermFormREDACTOR ter = new TermFormREDACTOR(ownerID, 1, sFields, isEdited);
                terms.add(ter);
                desc.add(ter);
                ter.adjustSize(getOffsetWidth() - 10);
                ter.form3.setWidget(4, 0, new HTML("Term number: " + desc.getWidgetCount()));
                ter.delete.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        new MyDialog("Are you sure that you would like to delete this term?", ter).show();
                    }
                });
            }
        });
    }

    public void refreshContentFromLangEntryDTO(final LangEntryDTO langEntryDTO, ArrayList<String> userLangs, HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited) {
        if (!langEntryDTO.listterm.isEmpty()) {
            int i = 0;
            for (final TermDTO tDTO : langEntryDTO.listterm) {
                if ((tDTO.getStatus() == 'e') || (tDTO.getStatus() == 'p')) {
                    final TermFormREDACTOR ter = new TermFormREDACTOR(ownerID, 0, sFields, isEdited);
                    i++;
                    terms.add(ter);
                    desc.add(ter);
                    ter.adjustSize(getOffsetWidth() - 10);
                    ter.refreshContentFromTermDTO(tDTO, userLangs, isEdited);
                    ter.form3.setWidget(4, 0, new HTML("Term number: " + i));
                    ter.delete.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            new MyDialog("Are you sure that you would like to delete this term?", ter).show();
                        }
                    });
                }
            }
        }
    }

    public void adjustSize(int w) {
        setWidth(w + "px");
        controls.setWidth(w + "px");
        controls.setCellHorizontalAlignment(addTerm, HorizontalPanel.ALIGN_RIGHT);
    }

    public void sortTermDTOByLangSet(ArrayList<LangEntryDTO> listlang) {
        removeDeletedTerms(listlang);
        if (!terms.isEmpty()) {
            for (TermFormREDACTOR tf : terms) {
                int i = getLangEntryIdx(tf.getIdLanguage(), listlang);
                if (i > -1) {
                    if (tf.type == 0) {
                        int j = getTermDTOIdx(tf.getTermID(), listlang.get(i).listterm);
                        tf.updateTermDTOFromContent(listlang.get(i).listterm.get(j));
                    } else {
                        TermDTO termDTO = new TermDTO(null);
                        tf.updateTermDTOFromContent(termDTO);
                        listlang.get(i).listterm.add(termDTO);
                    }
                } else {
                    TermDTO termDTO = new TermDTO(null);
                    tf.updateTermDTOFromContent(termDTO);
                    LangEntryDTO lsDTO = new LangEntryDTO(termDTO.getIdLanguage());
                    lsDTO.listterm.add(termDTO);
                    listlang.add(lsDTO);
                }
            }
        }
    }

    private int getLangEntryIdx(String langID, ArrayList<LangEntryDTO> listlang) {
        if (!listlang.isEmpty()) {
            int i = 0;
            for (LangEntryDTO lE : listlang) {
                if (lE.lan.getIdLanguage().equalsIgnoreCase(langID)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    private int getTermDTOIdx(Long termID, ArrayList<TermDTO> listterm) {
        if (!listterm.isEmpty()) {
            int i = 0;
            for (TermDTO tDTO : listterm) {
                if (tDTO.getIdTerm().equals(termID)) {
                    return i;
                }
                i++;
            }
        }
        return -1;
    }

    private class MyDialog extends DialogBox {

        public MyDialog(String text, final TermFormREDACTOR term) {
            // Set the dialog box's caption.
            setText(text);
            // Enable animation.
            setAnimationEnabled(true);
            // Enable glass background.
            setGlassEnabled(true);
            HorizontalPanel controls = new HorizontalPanel();
            // DialogBox is a SimplePanel, so you have to set its widget property to
            // whatever you want its contents to be.
            final Button submit = new Button("Delete");

            submit.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    deleteTermEntry(term);
                }
            });
            Button cancel = new Button("Cancel");
            cancel.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                }
            });
            setPopupPosition(100, 100);
            setWidth("400px");
            controls.add(cancel);
            controls.add(submit);
            setWidget(controls);
            controls.setWidth("400px");
            controls.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_LEFT);
            controls.setCellHorizontalAlignment(submit, HorizontalPanel.ALIGN_RIGHT);
        }
    }

    public void deleteTermEntry(final TermFormREDACTOR term) {
        if (term.getTermID() > -1) {
            getService().deleteTermEntry(term.getTermID(), new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {
                    MainEntryPoint.statusPanel.setMessage("error", "Could not delete Term");
                    History.newItem("page1");
                }

                @Override
                public void onSuccess(String result) {
                    terms.remove(term);
                    remterms.add(term);
                    term.removeFromParent();
                    MainEntryPoint.statusPanel.setMessage("message", "Term Deleted successfully");
                    History.newItem("page1");
                }
            });
        } else {
            terms.remove(term);
            term.removeFromParent();
            MainEntryPoint.statusPanel.setMessage("message", "Term Deleted successfully");
        }
    }

    private static myTermServiceAsync getService() {
        return GWT.create(myTermService.class);
    }

    private void removeDeletedTerms(ArrayList<LangEntryDTO> listlang) {
        for (TermFormREDACTOR trm : remterms) {
            if ((trm.getTermID() > -1) && (trm.getLangID().length() > 1)) {
                int i = getLangEntryIdx(trm.getIdLanguage(), listlang);
                if (i > 0) {
                    int j = getTermDTOIdx(trm.getTermID(), listlang.get(i).listterm);
                    if (j > 0) {
                        listlang.get(i).listterm.remove(j);
                    }
                }
            }
        }
        remterms.clear();
    }
}
