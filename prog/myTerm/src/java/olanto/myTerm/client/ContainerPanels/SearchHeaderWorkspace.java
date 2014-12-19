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
package olanto.myTerm.client.ContainerPanels;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.client.Domains.DomainList;
import olanto.myTerm.client.Langs.LangList;
import olanto.myTerm.client.Resources.ResourceList;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchHeaderWorkspace extends HorizontalPanel {

    public Label termLabel = new Label("Input your search expression: ");
    public TextBox searchField = new TextBox();
    public LangList langSrc;
    public LangList langTgt;
    public ResourceList rsrc;
    public DomainList dom = new DomainList();
    public Button btnSend = new Button("Search");
    public Button btnAdd = new Button("Add");

    public SearchHeaderWorkspace(long ownerID) {
        setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        add(termLabel);
        add(new HTML("&nbsp;"));
        add(searchField);
        add(new HTML("&nbsp;"));
        add(btnSend);
        add(new HTML("&nbsp;"));
        add(btnAdd);
        btnAdd.setTitle("Click to add a new entry");
        add(new HTML("&nbsp;"));
        langSrc = new LangList(ownerID, "source");
        add(new Label("Source Lang. "));
        add(new HTML("&nbsp;"));
        add(langSrc);
        add(new HTML("&nbsp;"));
        langTgt = new LangList(ownerID, "target");
        add(new Label("Target Lang. "));
        add(new HTML("&nbsp;"));
        add(langTgt);
        add(new HTML("&nbsp;"));
        rsrc = new ResourceList(ownerID, "workspace");
        add(new Label("Resource: "));
        add(new HTML("&nbsp;"));
        add(rsrc);
        add(new HTML("&nbsp;"));
        add(new Label("Domain: "));
        add(new HTML("&nbsp;"));
        add(dom);
        add(new HTML("&nbsp;"));
        setStyleName("searchMenu");
    }
}
