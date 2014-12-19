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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.client.Langs.LangList;
import olanto.myTerm.shared.TermDTO;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class TermForm extends VerticalPanel {

    private Grid form1 = new Grid(5, 2);
    private Grid form2 = new Grid(5, 2);
    public Grid form3 = new Grid(5, 2);
    private Label label_lng = new Label("Language:");
    private LangList lang;
    private Label label_frm = new Label("Term's form:");
    private TextArea text_frm = new TextArea();
    private Label label_src = new Label("Term's source:");
    private TextArea text_src = new TextArea();
    private Label label_def = new Label("Term's definition:");
    private TextArea text_def = new TextArea();
    private Label label_sdef = new Label("Definition's source:");
    private TextArea text_sdef = new TextArea();
    private Label label_usg = new Label("Term's usage:");
    private TextArea text_usg = new TextArea();
    private Label label_ctxt = new Label("Term's context:");
    private TextArea text_ctxt = new TextArea();
    private Label label_sctxt = new Label("Context's source:");
    private TextArea text_sctxt = new TextArea();
    private Label label_nt = new Label("Term's Note:");
    private TextArea text_nt = new TextArea();
    private Label label_tp = new Label("Type:");
    private TextArea text_tp = new TextArea();
    private Label label_pos = new Label("Part of speech:");
    private Label lang_lbl = new Label("");
    private TextBox text_pos = new TextBox();
    private Label label_gdr = new Label("Gender:");
    private TextBox text_gdr = new TextBox();
    private Label label_st = new Label("Status:");
    private TextBox text_st = new TextBox();
    private HorizontalPanel form = new HorizontalPanel();
    private HorizontalPanel controls = new HorizontalPanel();
    private Label label_ext = new Label("Extra:");
    private TextArea text_ext = new TextArea();
    public Button delete = new Button("Delete");
    public TermDTO termDTO;
    public int type;

    public TermForm(long ownerID, int type) {
        lang = new LangList(ownerID);
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
        form2.setWidget(1, 1, text_tp);
        form2.setWidget(2, 0, label_nt);
        form2.setWidget(2, 1, text_nt);
        form2.setWidget(3, 0, label_usg);
        form2.setWidget(3, 1, text_usg);
        form2.setWidget(4, 0, label_ext);
        form2.setWidget(4, 1, text_ext);

        form3.setWidget(0, 0, label_gdr);
        form3.setWidget(0, 1, text_gdr);
        form3.setWidget(1, 0, label_pos);
        form3.setWidget(1, 1, text_pos);
        form3.setWidget(2, 0, label_ctxt);
        form3.setWidget(2, 1, text_ctxt);
        form3.setWidget(3, 0, label_sctxt);
        form3.setWidget(3, 1, text_sctxt);
        form3.setWidget(4, 1, controls);
        text_st.setReadOnly(true);
        controls.add(delete);

//        more.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                if (!expanded) {
//                    rst.setVisible(true);
//                    more.setText("Less...");
//                    expanded = true;
//                } else {
//                    rst.setVisible(false);
//                    more.setText("Expand this...");
//                    expanded = false;
//                }
//            }
//        });
        delete.setTitle("Delete the current term");
        termDTO = new TermDTO();
    }

    public void refreshContentFromTermDTO() {
        text_frm.setText(termDTO.getTermForm());
        text_src.setText(termDTO.getTermSource());
        text_def.setText(termDTO.getTermDefinition());
        text_sdef.setText(termDTO.getTermSourceDefinition());
        text_usg.setText(termDTO.getTermUsage());
        text_ctxt.setText(termDTO.getTermContext());
        text_sctxt.setText(termDTO.getTermSourceContext());
        text_nt.setText(termDTO.getTermNote());
        text_tp.setText(termDTO.getTermType());
        text_pos.setText(termDTO.getTermPartofspeech());
        text_gdr.setText(termDTO.getTermGender());
        text_ext.setText(termDTO.getExtra());
        text_st.setText(termDTO.getStatus() + "");
        lang_lbl.setText(lang.getLangName(termDTO.getIdLanguage()));
        form1.setWidget(0, 1, lang_lbl);
    }

    public void adjustSize(int w) {
        controls.setWidth(w * 1 / 5 + "px");
        controls.setCellHorizontalAlignment(delete, HorizontalPanel.ALIGN_RIGHT);
        form.setWidth(w + "px");
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
        text_tp.setWidth(w * 1 / 5 + "px");
        text_pos.setWidth(w * 1 / 5 + "px");
        text_gdr.setWidth(w * 1 / 5 + "px");
        text_st.setWidth(w * 1 / 5 + "px");
        text_sdef.setWidth(w * 1 / 5 + "px");
        text_nt.setWidth(w * 1 / 5 + "px");
        text_ctxt.setWidth(w * 1 / 5 + "px");
        text_sctxt.setWidth(w * 1 / 5 + "px");
        text_usg.setWidth(w * 1 / 5 + "px");
        text_ext.setWidth(w * 1 / 5 + "px");
    }

    public void clearAllText() {
        text_frm.setText("");
        text_src.setText("");
        text_def.setText("");
        text_tp.setText("");
        text_pos.setText("");
        text_gdr.setText("");
        text_st.setText("");
        text_sdef.setText("");
        text_nt.setText("");
        text_ctxt.setText("");
        text_sctxt.setText("");
        text_usg.setText("");
        text_ext.setText("");
    }

    public void setReadOnly(Boolean edit) {
        text_frm.setReadOnly(edit);
        text_src.setReadOnly(edit);
        text_def.setReadOnly(edit);
        text_sdef.setReadOnly(edit);
        text_usg.setReadOnly(edit);
        text_ctxt.setReadOnly(edit);
        text_sctxt.setReadOnly(edit);
        text_nt.setReadOnly(edit);
        text_tp.setReadOnly(edit);
        text_pos.setReadOnly(edit);
        text_gdr.setReadOnly(edit);
        text_st.setReadOnly(true);
        text_ext.setReadOnly(edit);
    }

    public void gettTermDTOFromContent() {
        if (termDTO == null) {
            termDTO = new TermDTO();
        }
        termDTO.setTermForm(text_frm.getText());
        termDTO.setTermSource(text_src.getText());
        termDTO.setTermDefinition(text_def.getText());
        termDTO.setTermSourceDefinition(text_sdef.getText());
        termDTO.setTermUsage(text_usg.getText());
        termDTO.setTermContext(text_ctxt.getText());
        termDTO.setTermSourceContext(text_sctxt.getText());
        termDTO.setTermNote(text_nt.getText());
        termDTO.setTermType(text_tp.getText());
        termDTO.setTermPartofspeech(text_pos.getText());
        termDTO.setExtra(text_ext.getText());
        termDTO.setTermGender(text_gdr.getText());
        termDTO.setStatus('e');
        if (type == 0) {
            termDTO.setIdLanguage(lang.getLangID(lang_lbl.getText()));
        } else {
            termDTO.setIdLanguage(lang.getValue(lang.getSelectedIndex()));
        }
    }
}
