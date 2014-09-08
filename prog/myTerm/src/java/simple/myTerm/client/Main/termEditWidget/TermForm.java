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
package simple.myTerm.client.Main.termEditWidget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class TermForm extends VerticalPanel {

    private boolean expanded = false;
    private HorizontalPanel desc = new HorizontalPanel();
    private Grid impt = new Grid(4, 2);
    private Grid rst = new Grid(8, 2);
    private Anchor more = new Anchor("Expand this...");
    private Label label_frm = new Label("Term's form:");
    private TextBox text_frm = new TextBox();
    private Label label_src = new Label("Term's fource:");
    private TextBox text_src = new TextBox();
    private Label label_def = new Label("Term's definition:");
    private TextBox text_def = new TextBox();
    private Label label_sdef = new Label("Definition's source:");
    private TextBox text_sdef = new TextBox();
    private Label label_usg = new Label("Term's usage:");
    private TextBox text_usg = new TextBox();
    private Label label_ctxt = new Label("Term's context:");
    private TextBox text_ctxt = new TextBox();
    private Label label_sctxt = new Label("Context's source:");
    private TextBox text_sctxt = new TextBox();
    private Label label_nt = new Label("Note:");
    private TextBox text_nt = new TextBox();
    private Label label_tp = new Label("Type:");
    private TextBox text_tp = new TextBox();
    private Label label_pos = new Label("Part of speech:");
    private TextBox text_pos = new TextBox();
    private Label label_gdr = new Label("Gender:");
    private TextBox text_gdr = new TextBox();
    private Label label_st = new Label("Status:");
    private TextBox text_st = new TextBox();
    private Button submit = new Button("Submit");

    public TermForm() {
        this.setStyleName("termForm");
        add(desc);
        add(impt);
        add(more);
        add(rst);
        add(submit);
        impt.setStyleName("panel");
        rst.setStyleName("panel");
        impt.setCellSpacing(6);
        rst.setCellSpacing(6);

        impt.setWidget(0, 0, label_frm);
        impt.setWidget(0, 1, text_frm);
        impt.setWidget(1, 0, label_src);
        impt.setWidget(1, 1, text_src);
        impt.setWidget(2, 0, label_def);
        impt.setWidget(2, 1, text_def);
        impt.setWidget(3, 0, label_sdef);
        impt.setWidget(3, 1, text_sdef);

        rst.setWidget(0, 0, label_usg);
        rst.setWidget(0, 1, text_usg);
        rst.setWidget(1, 0, label_ctxt);
        rst.setWidget(1, 1, text_ctxt);
        rst.setWidget(2, 0, label_sctxt);
        rst.setWidget(2, 1, text_sctxt);
        rst.setWidget(3, 0, label_nt);
        rst.setWidget(3, 1, text_nt);
        rst.setWidget(4, 0, label_tp);
        rst.setWidget(4, 1, text_tp);
        rst.setWidget(5, 0, label_pos);
        rst.setWidget(5, 1, text_pos);
        rst.setWidget(6, 0, label_gdr);
        rst.setWidget(6, 1, text_gdr);
        rst.setWidget(7, 0, label_st);
        rst.setWidget(7, 1, text_st);
        setCellHorizontalAlignment(submit, HorizontalPanel.ALIGN_RIGHT);
        rst.setVisible(false);

        more.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (!expanded) {
                    rst.setVisible(true);
                    more.setText("Less...");
                    expanded = true;
                } else {
                    rst.setVisible(false);
                    more.setText("Expand this...");
                    expanded = false;
                }
            }
        });
    }

    public void setReadOnly(boolean editable) {
        text_frm.setReadOnly(editable);
        text_src.setReadOnly(editable);
        text_def.setReadOnly(editable);
        text_sdef.setReadOnly(editable);
        text_usg.setReadOnly(editable);
        text_ctxt.setReadOnly(editable);
        text_sctxt.setReadOnly(editable);
        text_nt.setReadOnly(editable);
        text_tp.setReadOnly(editable);
        text_pos.setReadOnly(editable);
        text_gdr.setReadOnly(editable);
        text_st.setReadOnly(editable);
    }

    @Override
    public void clear() {
        text_frm.setText("");
        text_src.setText("");
        text_def.setText("");
        text_sdef.setText("");
        text_usg.setText("");
        text_ctxt.setText("");
        text_sctxt.setText("");
        text_nt.setText("");
        text_tp.setText("");
        text_pos.setText("");
        text_gdr.setText("");
        text_st.setText("");
    }
}
