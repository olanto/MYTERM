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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import olanto.myTerm.client.Lists.LangList;
import olanto.myTerm.client.Lists.PartofSpeechList;
import olanto.myTerm.client.Lists.TermGenderList;
import olanto.myTerm.client.Lists.TermTypeList;
import olanto.myTerm.client.ObjectWrappers.BooleanWrap;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.TermDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class TermFormREDACTOR extends VerticalPanel {

    private Grid form1;
    private Grid form2;
    public Grid form3;
    private Label label_lng;
    private LangList lang;
    private Label label_frm;
    private TextAreaMyTerm text_frm;
    private Label label_src;
    private TextAreaMyTerm text_src;
    private Label label_def;
    private TextAreaMyTerm text_def;
    private Label label_sdef;
    private TextAreaMyTerm text_sdef;
    private Label label_usg;
    private TextAreaMyTerm text_usg;
    private Label label_ctxt;
    private TextAreaMyTerm text_ctxt;
    private Label label_sctxt;
    private TextAreaMyTerm text_sctxt;
    private Label label_nt;
    private TextAreaMyTerm text_nt;
    private Label label_tp;
    private TermTypeList term_type;
    private Label label_pos;
    private PartofSpeechList term_pos;
    private Label label_gdr;
    private TermGenderList term_gdr;
    private Label label_st;
    private Label text_st;
    private HorizontalPanel form;
    private HorizontalPanel controls;
    private Label label_ext;
    private TextAreaMyTerm text_ext;
    public Button delete;
    public Button edit;
    public int type;
    private long ownerID;
    private long termID;
    private String langID;
    private Label lang_lbl;
    private char status;

    public TermFormREDACTOR(long ownerID, int type, HashMap<String, SysFieldDTO> sFields, BooleanWrap isEdited) {

        form1 = new Grid(5, 2);
        form2 = new Grid(5, 2);
        form3 = new Grid(5, 2);
        label_lng = new Label("Language:");
        label_frm = new Label("Term's form:");
        text_frm = new TextAreaMyTerm("t.form", sFields, isEdited);
        label_src = new Label("Term's source:");
        text_src = new TextAreaMyTerm("t.source", sFields, isEdited);
        label_def = new Label("Term's definition:");
        text_def = new TextAreaMyTerm("t.definition", sFields, isEdited);
        label_sdef = new Label("Definition's source:");
        text_sdef = new TextAreaMyTerm("t.source_definition", sFields, isEdited);
        label_usg = new Label("Term's usage:");
        text_usg = new TextAreaMyTerm("t.usage", sFields, isEdited);
        label_ctxt = new Label("Term's context:");
        text_ctxt = new TextAreaMyTerm("t.context", sFields, isEdited);
        label_sctxt = new Label("Context's source:");
        text_sctxt = new TextAreaMyTerm("t.source_context", sFields, isEdited);
        label_nt = new Label("Term's Note:");
        text_nt = new TextAreaMyTerm("t.note", sFields, isEdited);
        label_tp = new Label("Type:");
        label_pos = new Label("Part of speech:");
        label_gdr = new Label("Gender:");
        label_st = new Label("Status:");
        text_st = new Label();
        form = new HorizontalPanel();
        controls = new HorizontalPanel();
        label_ext = new Label("Extra:");
        text_ext = new TextAreaMyTerm("t.extra", sFields, isEdited);
        delete = new Button("Delete");
        edit = new Button("Edit");
        termID = -1;
        langID = "";
        lang_lbl = new Label("");
        status = 'e';
        lang = new LangList(ownerID, isEdited);
        term_type = new TermTypeList("EN", isEdited);
        term_pos = new PartofSpeechList("EN", isEdited);
        term_gdr = new TermGenderList("EN", isEdited);
        this.ownerID = ownerID;
        this.type = type;
        this.setStyleName("termForm");
        add(form);
        form.add(form1);
        form.add(form2);
        form.add(form3);
        form1.setStyleName("edpanel");
        form2.setStyleName("edpanel");
        form3.setStyleName("edpanel");
        form1.setCellSpacing(6);
        form2.setCellSpacing(6);
        form3.setCellSpacing(6);
        form1.setWidget(0, 0, label_lng);
        form1.setWidget(0, 1, lang);
        form1.setWidget(1, 0, label_frm);
        form1.setWidget(1, 1, text_frm);
        form1.setWidget(2, 0, label_src);
        form1.setWidget(2, 1, text_src);
        form1.setWidget(3, 0, label_def);
        form1.setWidget(3, 1, text_def);
        form1.setWidget(4, 0, label_sdef);
        form1.setWidget(4, 1, text_sdef);
        form2.setWidget(0, 0, label_st);
        form2.setWidget(0, 1, text_st);
        form2.setWidget(1, 0, label_tp);
        form2.setWidget(1, 1, term_type);
        form2.setWidget(2, 0, label_nt);
        form2.setWidget(2, 1, text_nt);
        form2.setWidget(3, 0, label_usg);
        form2.setWidget(3, 1, text_usg);
        form2.setWidget(4, 0, label_ext);
        form2.setWidget(4, 1, text_ext);
        form3.setWidget(0, 0, label_gdr);
        form3.setWidget(0, 1, term_gdr);
        form3.setWidget(1, 0, label_pos);
        form3.setWidget(1, 1, term_pos);
        form3.setWidget(2, 0, label_ctxt);
        form3.setWidget(2, 1, text_ctxt);
        form3.setWidget(3, 0, label_sctxt);
        form3.setWidget(3, 1, text_sctxt);
        form3.setWidget(4, 1, controls);
        controls.add(edit);
        controls.add(delete);
        delete.setTitle("Delete the current term");
        edit.setTitle("Edit the current term");
        edit.setEnabled(false);
        text_frm.setText("");
        text_src.setText("");
        text_def.setText("");
        text_st.setText("");
        text_sdef.setText("");
        text_nt.setText("");
        text_ctxt.setText("");
        text_sctxt.setText("");
        text_usg.setText("");
        text_ext.setText("");
        edit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setReadOnly(false);
            }
        });
    }

    public void refreshContentFromTermDTO(TermDTO termDTO, ArrayList<String> userLangs, BooleanWrap isEdited) {
        termID = termDTO.getIdTerm();
        text_frm.setText(termDTO.getTermForm());
        text_src.setText(termDTO.getTermSource());
        text_def.setText(termDTO.getTermDefinition());
        text_sdef.setText(termDTO.getTermSourceDefinition());
        text_usg.setText(termDTO.getTermUsage());
        text_ctxt.setText(termDTO.getTermContext());
        text_sctxt.setText(termDTO.getTermSourceContext());
        text_nt.setText(termDTO.getTermNote());
        form3.remove(term_pos);
        term_pos = new PartofSpeechList("EN", termDTO.getTermPartofspeech(), isEdited);
        form3.setWidget(1, 1, term_pos);
        form3.remove(term_gdr);
        term_gdr = new TermGenderList("EN", termDTO.getTermGender(), isEdited);
        form3.setWidget(0, 1, term_gdr);
        text_ext.setText(termDTO.getExtra());
        text_st.setText(termDTO.getStatus() + "");
        lang_lbl.setText(termDTO.getLangName());
        lang_lbl.setTitle(termDTO.getIdLanguage());
        langID = termDTO.getIdLanguage();
        form1.remove(lang);
        form1.setWidget(0, 1, lang_lbl);
        form2.remove(term_type);
        term_type = new TermTypeList("EN", termDTO.getTermType(), isEdited);
        form2.setWidget(1, 1, term_type);
        status = termDTO.getStatus();
        if ((status == 'e') && (userLangs.contains(termDTO.getIdLanguage()))) {
            this.setReadOnly(false);
        } else {
            this.setReadOnly(true);
        }
    }

    public void adjustSize(int w) {
        controls.setWidth(w * 1 / 5 + "px");
        controls.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_RIGHT);
        controls.setCellHorizontalAlignment(edit, HorizontalPanel.ALIGN_LEFT);
        form.setWidth(w * 1 / 3 + "px");
        form.setCellHorizontalAlignment(form1, HorizontalPanel.ALIGN_LEFT);
        form.setCellHorizontalAlignment(form2, HorizontalPanel.ALIGN_CENTER);
        form.setCellHorizontalAlignment(form3, HorizontalPanel.ALIGN_RIGHT);
        form1.setWidth(w * 1 / 3 + "px");
        form2.setWidth(w * 1 / 3 + "px");
        form3.setWidth(w * 1 / 3 + "px");
        lang.setWidth(w * 1 / 5 + "px");
        text_frm.setWidth(w * 1 / 5 + "px");
        text_src.setWidth(w * 1 / 5 + "px");
        text_def.setWidth(w * 1 / 5 + "px");
        term_type.setWidth(w * 1 / 5 + "px");
        term_pos.setWidth(w * 1 / 5 + "px");
        term_gdr.setWidth(w * 1 / 5 + "px");
        text_st.setWidth(w * 1 / 5 + "px");
        text_sdef.setWidth(w * 1 / 5 + "px");
        text_nt.setWidth(w * 1 / 5 + "px");
        text_ctxt.setWidth(w * 1 / 5 + "px");
        text_sctxt.setWidth(w * 1 / 5 + "px");
        text_usg.setWidth(w * 1 / 5 + "px");
        text_ext.setWidth(w * 1 / 5 + "px");
        lang_lbl.setWidth(w * 1 / 5 + "px");
    }

    public void setReadOnly(Boolean isReadOnly) {
        text_frm.setReadOnly(isReadOnly);
        text_src.setReadOnly(isReadOnly);
        text_def.setReadOnly(isReadOnly);
        text_sdef.setReadOnly(isReadOnly);
        text_usg.setReadOnly(isReadOnly);
        text_ctxt.setReadOnly(isReadOnly);
        text_sctxt.setReadOnly(isReadOnly);
        text_nt.setReadOnly(isReadOnly);
        term_type.setEnabled(!isReadOnly);
        term_pos.setEnabled(!isReadOnly);
        term_gdr.setEnabled(!isReadOnly);
        text_ext.setReadOnly(isReadOnly);
        delete.setEnabled(!isReadOnly);
        edit.setEnabled(isReadOnly);
    }

    public String getTermForm() {
        return text_frm.getText();
    }

    public Long getTermID() {
        return termID;
    }

    public String getLangID() {
        return langID;
    }

    public String getIdLanguage() {
        if (type == 0) {
            return lang_lbl.getTitle();
        } else {
            return lang.getValue(lang.getSelectedIndex());
        }
    }

    public void updateTermDTOFromContent(TermDTO termDTO) {
        termDTO.setTermForm(text_frm.getText());
        termDTO.setTermSource(text_src.getText());
        termDTO.setTermDefinition(text_def.getText());
        termDTO.setTermSourceDefinition(text_sdef.getText());
        termDTO.setTermUsage(text_usg.getText());
        termDTO.setTermContext(text_ctxt.getText());
        termDTO.setTermSourceContext(text_sctxt.getText());
        termDTO.setTermNote(text_nt.getText());
        termDTO.setExtra(text_ext.getText());
        termDTO.setLastmodified(new Date(System.currentTimeMillis()));
        termDTO.setLastmodifiedBy(BigInteger.valueOf(ownerID));
        termDTO.setStatus(status);
        if (type == 0) {
            termDTO.setIdLanguage(lang_lbl.getTitle());
            termDTO.setLangName(lang_lbl.getText());
        } else {
            termDTO.setIdLanguage(lang.getValue(lang.getSelectedIndex()));
            termDTO.setLangName(lang.getItemText(lang.getSelectedIndex()));
        }
        termDTO.setTermType(term_type.getValue(term_type.getSelectedIndex()));
        termDTO.setTermPartofspeech(term_pos.getValue(term_pos.getSelectedIndex()));
        termDTO.setTermGender(term_gdr.getValue(term_gdr.getSelectedIndex()));
    }
}
