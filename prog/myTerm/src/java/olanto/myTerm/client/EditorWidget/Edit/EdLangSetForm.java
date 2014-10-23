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
package olanto.myTerm.client.EditorWidget.Edit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.client.Langs.LangList;
import olanto.myTerm.client.Types.Term;

/**
 * Form for adding a new term in a given lanSet of a given concept
 *
 * @author nizar ghoula - simple
 */
public class EdLangSetForm extends VerticalPanel {

    private VerticalPanel desc = new VerticalPanel();
    private HorizontalPanel langs = new HorizontalPanel();
    private Label label_lng = new Label("Language:");
    private LangList lang = new LangList();
    private HorizontalPanel controls = new HorizontalPanel();
    public Button addTerm = new Button("Add Term");
    public Button addLang = new Button("Add Language");
    public Button cancel = new Button("cancel");
    EdTermForm ter = new EdTermForm();

    public EdLangSetForm() {
        this.setStyleName("langSetForm");
        add(langs);
        add(desc);
        desc.add(ter);
        add(controls);
        controls.add(addTerm);
        controls.add(addLang);
        controls.add(cancel);
        langs.setStyleName("lspanel");
        langs.add(label_lng);
        langs.add(lang);
        controls.setCellHorizontalAlignment(addTerm, HorizontalPanel.ALIGN_LEFT);
        controls.setCellHorizontalAlignment(cancel, HorizontalPanel.ALIGN_CENTER);
        controls.setCellHorizontalAlignment(addLang, HorizontalPanel.ALIGN_RIGHT);
        addTerm.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                final EdTermForm tr = new EdTermForm();
                desc.add(tr);
            }
        });
        cancel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                setVisible(false);
            }
        });
        addLang.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                EdLangSetForm addt = new EdLangSetForm();
                add(addt);
            }
        });
    }

    public void initfromvar(Term t) {
        ter.initFormVariable(t);
        lang.selectlanguage(t.language);
    }
}
