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
package simple.client.Main.EditorWidget.Edit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import simple.client.Main.Types.Term;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class EdTermForm extends VerticalPanel {

    private boolean expanded = false;
    private Grid impt = new Grid(4, 2);
    private Grid rst = new Grid(8, 2);
    private Anchor more = new Anchor("Expand this...");
    private Label label_frm = new Label("Term's form:");
    private TextBox text_frm = new TextBox();
    private Label label_src = new Label("Term's source:");
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
    private HorizontalPanel controls = new HorizontalPanel();
    public Button cancel = new Button("Cancel");
    public Button submit = new Button("Submit");

    public EdTermForm() {
        this.setStyleName("termForm");
        add(impt);
        add(more);
        setCellHorizontalAlignment(more, ALIGN_RIGHT);
        add(rst);
        add(controls);
        controls.add(cancel);
        controls.add(submit);
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
        controls.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_CENTER);

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
        cancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setVisible(false);
            }
        });
    }

    public void initFormVariable(Term t) {
        text_frm.setText(t.form);
        text_src.setText(t.source);
        text_def.setText(t.definition);
        text_sdef.setText(t.definition_source);
        text_usg.setText(t.usage);
        text_ctxt.setText(t.context);
        text_sctxt.setText(t.context_source);
        text_nt.setText(t.note);
        text_tp.setText(t.type);
        text_pos.setText(t.part_of_speech);
        text_gdr.setText(t.gender);
        text_st.setText(t.status);
    }

    public void clearAllText() {
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
