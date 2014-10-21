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
package simple.client.Main.EditorWidget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_JUSTIFY;
import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import simple.client.Main.Langs.LangList;
import simple.client.Main.Resources.ResourceList;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchHeaderWorkspace extends HorizontalPanel {

    public Label termLabel = new Label("Input your search expression: ");
    public TextBox searchField = new TextBox();
    public LangList langSrc = new LangList("source");
    public LangList langTgt = new LangList("target");
    public ResourceList rsrc = new ResourceList();
    public Button btnSend = new Button("Search");
    public Button btnAdd = new Button("Add");

    public SearchHeaderWorkspace() {
        setWidth((Window.getClientWidth() - 20) + "px");
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_JUSTIFY);
        add(termLabel);
        add(new HTML("&nbsp;"));
        add(searchField);
        add(new HTML("&nbsp;"));
        add(btnSend);
        add(new HTML("&nbsp;"));
        add(btnAdd);
        btnAdd.setTitle("Click to add a new entry");
        add(new HTML("&nbsp;"));
        add(new Label("Source Lang. "));
        add(new HTML("&nbsp;"));
        add(langSrc);
        add(new HTML("&nbsp;"));
        add(new Label("Target Lang. "));
        add(new HTML("&nbsp;"));
        add(langTgt);
        add(new HTML("&nbsp;"));
        add(new Label("Resource: "));
        add(new HTML("&nbsp;"));
        add(rsrc);
        add(new HTML("&nbsp;"));
        setStyleName("searchMenu");
    }
}