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
import olanto.myTerm.shared.GuiConstant;
import olanto.myTerm.shared.SysFieldDTO;
import olanto.myTerm.shared.TermDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class TermFormREDACTOR extends VerticalPanel {

    /* term list gender, type etc. need to be also hidden (fix it for next release*/
    private Grid form1;
    private Grid form2;
    public Grid form3;
    private Label created_by;
    private Label created_by_ct;
    private Label last_modif_by;
    private Label last_modif_by_ct;
    private Label label_lng;
    private LangList lang;
    private Label label_frm;
    private TextBoxMyTerm text_frm;
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
    private Label label_techNt;
    private TextAreaMyTerm text_techNt;
    private Label label_stechNt;
    private TextAreaMyTerm text_stechNt;
    private Label label_lingNt;
    private TextAreaMyTerm text_lingNt;
    private Label label_refNt;
    private TextAreaMyTerm text_refNt;
    public Button delete;
    public Button edit;
    public int type;
    private long ownerID;
    private long termID;
    private String langID;
    private Label lang_lbl;
    private char status;
    private BooleanWrap isLocallyEdited = new BooleanWrap();

    public TermFormREDACTOR(long ownerID, int type, HashMap<String, SysFieldDTO> sFields, final BooleanWrap isEdited, HashMap<String, String> sysMsg) {
        form1 = new Grid(7, 2);
        form2 = new Grid(7, 2);
        form3 = new Grid(7, 2);
        created_by = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_CREATED_BY), sFields.get(GuiConstant.T_CREATED_BY));
        created_by_ct = new LabelMyTerm("", sFields.get(GuiConstant.T_CREATED_BY));
        last_modif_by = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_LAST_MODIF_BY), sFields.get(GuiConstant.T_LAST_MODIF_BY));
        last_modif_by_ct = new LabelMyTerm("", sFields.get(GuiConstant.T_LAST_MODIF_BY));
        label_lng = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_LANG), sFields.get(GuiConstant.T_LANG));
        label_frm = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_FORM), sFields.get(GuiConstant.T_FORM));
        text_frm = new TextBoxMyTerm(sFields.get(GuiConstant.T_FORM), isEdited, isLocallyEdited);
        label_src = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_SOURCE), sFields.get(GuiConstant.T_SOURCE));
        text_src = new TextAreaMyTerm(sFields.get(GuiConstant.T_SOURCE), isEdited, isLocallyEdited);
        label_def = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_DEFINITION), sFields.get(GuiConstant.T_DEFINITION));
        text_def = new TextAreaMyTerm(sFields.get(GuiConstant.T_DEFINITION), isEdited, isLocallyEdited);
        label_sdef = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_SOURCE_DEFINITION), sFields.get(GuiConstant.T_SOURCE_DEFINITION));
        text_sdef = new TextAreaMyTerm(sFields.get(GuiConstant.T_SOURCE_DEFINITION), isEdited, isLocallyEdited);
        label_usg = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_USAGE), sFields.get(GuiConstant.T_USAGE));
        text_usg = new TextAreaMyTerm(sFields.get(GuiConstant.T_USAGE), isEdited, isLocallyEdited);
        label_ctxt = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_CONTEXT), sFields.get(GuiConstant.T_CONTEXT));
        text_ctxt = new TextAreaMyTerm(sFields.get(GuiConstant.T_CONTEXT), isEdited, isLocallyEdited);
        label_sctxt = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_SOURCE_CONTEXT), sFields.get(GuiConstant.T_SOURCE_CONTEXT));
        text_sctxt = new TextAreaMyTerm(sFields.get(GuiConstant.T_SOURCE_CONTEXT), isEdited, isLocallyEdited);
        label_nt = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_NOTE), sFields.get(GuiConstant.T_NOTE));
        text_nt = new TextAreaMyTerm(sFields.get(GuiConstant.T_NOTE), isEdited, isLocallyEdited);
        label_tp = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_TYPE), sFields.get(GuiConstant.T_TYPE));
        label_pos = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_POS), sFields.get(GuiConstant.T_POS));
        label_gdr = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_GENDER), sFields.get(GuiConstant.T_GENDER));
        label_st = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_STATUS), sFields.get(GuiConstant.T_STATUS));
        term_type = new TermTypeList(GuiConstant.INTERFACE_LANG, isEdited, isLocallyEdited, sFields.get(GuiConstant.T_TYPE));
        term_pos = new PartofSpeechList(GuiConstant.INTERFACE_LANG, isEdited, isLocallyEdited, sFields.get(GuiConstant.T_POS));
        term_gdr = new TermGenderList(GuiConstant.INTERFACE_LANG, isEdited, isLocallyEdited, sFields.get(GuiConstant.T_GENDER));
        label_ext = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_EXTRA), sFields.get(GuiConstant.T_EXTRA));
        text_ext = new TextAreaMyTerm(sFields.get(GuiConstant.T_EXTRA), isEdited, isLocallyEdited);
        label_techNt = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_TECH_NOTE), sFields.get(GuiConstant.T_TECH_NOTE));
        text_techNt = new TextAreaMyTerm(sFields.get(GuiConstant.T_TECH_NOTE), isEdited, isLocallyEdited);
        label_stechNt = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_TECH_NOTE_SRC), sFields.get(GuiConstant.T_TECH_NOTE_SRC));
        text_stechNt = new TextAreaMyTerm(sFields.get(GuiConstant.T_TECH_NOTE_SRC), isEdited, isLocallyEdited);
        label_lingNt = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_LING_NOTE), sFields.get(GuiConstant.T_LING_NOTE));
        text_lingNt = new TextAreaMyTerm(sFields.get(GuiConstant.T_LING_NOTE), isEdited, isLocallyEdited);
        label_refNt = new LabelMyTerm(sysMsg.get(GuiConstant.LBL_T_REFERENCE_NOTE), sFields.get(GuiConstant.T_REFERENCE_NOTE));
        text_refNt = new TextAreaMyTerm(sFields.get(GuiConstant.T_REFERENCE_NOTE), isEdited, isLocallyEdited);
        text_st = new Label();
        form = new HorizontalPanel();
        controls = new HorizontalPanel();
        delete = new Button(sysMsg.get(GuiConstant.BTN_DELETE));
        edit = new Button(sysMsg.get(GuiConstant.BTN_EDIT));
        termID = -1;
        langID = "";
        lang_lbl = new Label("");
        status = 'e';
        lang = new LangList(ownerID, isEdited, isLocallyEdited);
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
        form1.setCellSpacing(3);
        form2.setCellSpacing(3);
        form3.setCellSpacing(3);

        form1.setWidget(0, 0, created_by);
        form1.setWidget(0, 1, created_by_ct);
        form1.setWidget(1, 0, last_modif_by);
        form1.setWidget(1, 1, last_modif_by_ct);
        form1.setWidget(2, 0, label_lng);
        form1.setWidget(2, 1, lang);
        form1.setWidget(3, 0, label_frm);
        form1.setWidget(3, 1, text_frm);
        form1.setWidget(4, 0, label_src);
        form1.setWidget(4, 1, text_src);
        form1.setWidget(5, 0, label_def);
        form1.setWidget(5, 1, text_def);
        form1.setWidget(6, 0, label_sdef);
        form1.setWidget(6, 1, text_sdef);

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
        form2.setWidget(5, 0, label_techNt);
        form2.setWidget(5, 1, text_techNt);
        form2.setWidget(6, 0, label_stechNt);
        form2.setWidget(6, 1, text_stechNt);

        form3.setWidget(0, 0, label_gdr);
        form3.setWidget(0, 1, term_gdr);
        form3.setWidget(1, 0, label_pos);
        form3.setWidget(1, 1, term_pos);
        form3.setWidget(2, 0, label_ctxt);
        form3.setWidget(2, 1, text_ctxt);
        form3.setWidget(3, 0, label_sctxt);
        form3.setWidget(3, 1, text_sctxt);
        form3.setWidget(4, 0, label_lingNt);
        form3.setWidget(4, 1, text_lingNt);
        form3.setWidget(5, 0, label_refNt);
        form3.setWidget(5, 1, text_refNt);

        form3.setWidget(6, 1, controls);
        controls.add(edit);
        controls.add(delete);
        text_st.setText(sysMsg.get(GuiConstant.MSG_STATUS_ED));
        delete.setTitle("Delete the current term");
        edit.setTitle("Edit the current term");
        edit.setEnabled(false);
        text_frm.setText("");
        text_src.setText("");
        text_def.setText("");
        text_st.setText(sysMsg.get(GuiConstant.MSG_STATUS_ED));
        text_sdef.setText("");
        text_nt.setText("");
        text_ctxt.setText("");
        text_sctxt.setText("");
        text_usg.setText("");
        text_ext.setText("");

        text_techNt.setText("");
        text_stechNt.setText("");
        text_lingNt.setText("");
        text_refNt.setText("");

        edit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setReadOnly(false);
            }
        });
    }

    public void refreshContentFromTermDTO(TermDTO termDTO, ArrayList<String> userLangs, BooleanWrap isEdited, HashMap<String, String> sysMsg, HashMap<String, SysFieldDTO> sFields) {
        termID = termDTO.getIdTerm();
        status = termDTO.getStatus();
        text_frm.setText(termDTO.getTermForm());
        text_src.setText(termDTO.getTermSource());
        text_def.setText(termDTO.getTermDefinition());
        text_sdef.setText(termDTO.getTermSourceDefinition());
        text_usg.setText(termDTO.getTermUsage());
        text_ctxt.setText(termDTO.getTermContext());
        text_sctxt.setText(termDTO.getTermSourceContext());
        text_nt.setText(termDTO.getTermNote());

        text_techNt.setText(termDTO.getTechnicalNote());
        text_stechNt.setText(termDTO.getTechnicalNoteSource());
        text_lingNt.setText(termDTO.getLinguisticNote());
        text_refNt.setText(termDTO.getReferenceNote());

        created_by_ct.setText(termDTO.getCreatedBy());
        last_modif_by_ct.setText(termDTO.getLastModifiedBy());

        form3.remove(term_pos);
        term_pos = new PartofSpeechList(GuiConstant.INTERFACE_LANG, termDTO.getTermPartofspeech(), isEdited, isLocallyEdited, sFields.get(GuiConstant.T_POS));
        form3.setWidget(1, 1, term_pos);
        form3.remove(term_gdr);
        term_gdr = new TermGenderList(GuiConstant.INTERFACE_LANG, termDTO.getTermGender(), isEdited, isLocallyEdited, sFields.get(GuiConstant.T_GENDER));
        form3.setWidget(0, 1, term_gdr);
        text_ext.setText(termDTO.getExtra());
        switch (status) {
            case 'e':
                text_st.setText(sysMsg.get(GuiConstant.MSG_STATUS_ED));
                break;
            case 'p':
                text_st.setText(sysMsg.get(GuiConstant.MSG_STATUS_PUB));
                break;
            case 'r':
                text_st.setText(sysMsg.get(GuiConstant.MSG_STATUS_REV));
                break;
        }
        lang_lbl.setText(termDTO.getLangName());
        lang_lbl.setTitle(termDTO.getIdLanguage());
        langID = termDTO.getIdLanguage();
        form1.remove(lang);
        form1.setWidget(2, 1, lang_lbl);
        form2.remove(term_type);
        term_type = new TermTypeList(GuiConstant.INTERFACE_LANG, termDTO.getTermType(), isEdited, isLocallyEdited, sFields.get(GuiConstant.T_TYPE));
        form2.setWidget(1, 1, term_type);
        if ((status == 'e') && (userLangs.contains(termDTO.getIdLanguage()))) {
            this.setReadOnly(false);
        } else {
            this.setReadOnly(true);
        }
    }

    public void adjustSize(int w) {
        controls.setWidth(w * 1 / 4 + "px");
        controls.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_RIGHT);
        controls.setCellHorizontalAlignment(edit, HorizontalPanel.ALIGN_LEFT);
        form.setWidth(w * 1 / 3 + "px");
        form.setCellHorizontalAlignment(form1, HorizontalPanel.ALIGN_LEFT);
        form.setCellHorizontalAlignment(form2, HorizontalPanel.ALIGN_CENTER);
        form.setCellHorizontalAlignment(form3, HorizontalPanel.ALIGN_RIGHT);
        form1.setWidth(w * 1 / 3 + "px");
        form2.setWidth(w * 1 / 3 + "px");
        form3.setWidth(w * 1 / 3 + "px");
        lang.setWidth(w * 1 / 4 + "px");
        text_frm.setWidth(w * 1 / 4 + "px");
        text_src.setWidth(w * 1 / 4 + "px");
        text_def.setWidth(w * 1 / 4 + "px");
        term_type.setWidth(w * 1 / 4 + "px");
        term_pos.setWidth(w * 1 / 4 + "px");
        term_gdr.setWidth(w * 1 / 4 + "px");
        text_st.setWidth(w * 1 / 4 + "px");
        text_sdef.setWidth(w * 1 / 4 + "px");
        text_nt.setWidth(w * 1 / 4 + "px");
        text_ctxt.setWidth(w * 1 / 4 + "px");
        text_sctxt.setWidth(w * 1 / 4 + "px");
        text_usg.setWidth(w * 1 / 4 + "px");
        text_ext.setWidth(w * 1 / 4 + "px");
        lang_lbl.setWidth(w * 1 / 4 + "px");
        text_techNt.setWidth(w * 1 / 4 + "px");
        text_stechNt.setWidth(w * 1 / 4 + "px");
        text_lingNt.setWidth(w * 1 / 4 + "px");
        text_refNt.setWidth(w * 1 / 4 + "px");
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
        text_techNt.setReadOnly(isReadOnly);
        text_stechNt.setReadOnly(isReadOnly);
        text_lingNt.setReadOnly(isReadOnly);
        text_refNt.setReadOnly(isReadOnly);

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

        termDTO.setTechnicalNote(text_techNt.getText());
        termDTO.setTechnicalNoteSource(text_stechNt.getText());
        termDTO.setLinguisticNote(text_lingNt.getText());
        termDTO.setReferenceNote(text_refNt.getText());

        termDTO.setLastmodified(new Date(System.currentTimeMillis()));
        termDTO.setModifiedBy(BigInteger.valueOf(ownerID));

        if (termDTO.getCreateBy() == null) {
            termDTO.setCreation(new Date(System.currentTimeMillis()));
            termDTO.setCreateBy(BigInteger.valueOf(ownerID));
        }
        if (isLocallyEdited.getVal()) {
            termDTO.setStatus('e');
        } else {
            termDTO.setStatus(status);
        }
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
