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
package simple.myTerm.client.Main.ValidatorWidget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author nizar ghoula - simple
 */
public class ExpSearchResultsContainer extends HorizontalPanel {

    public ScrollPanel termsPan = new ScrollPanel();
    public ScrollPanel resultsPan = new ScrollPanel();
    public ExpConceptPanel expres = new ExpConceptPanel();
    public VerticalPanel vp = new VerticalPanel();
    public HTMLPanel res = new HTMLPanel("");

    public ExpSearchResultsContainer() {
        add(termsPan);
        add(resultsPan);
        termsPan.setStyleName("sideWidget");
        resultsPan.setStyleName("containerWidget");
        resultsPan.add(vp);
        vp.add(expres);
        vp.add(res);
        resultsPan.setPixelSize(Window.getClientWidth() * 3 / 5, (Window.getClientHeight() - 200));
        termsPan.setPixelSize(Window.getClientWidth() * 2 / 5, (Window.getClientHeight() - 200));
        setCellHorizontalAlignment(termsPan, HorizontalPanel.ALIGN_LEFT);
        setCellHorizontalAlignment(resultsPan, HorizontalPanel.ALIGN_CENTER);
    }

    public void adjustHeight(int height) {
        resultsPan.setHeight(height + "px");
        termsPan.setHeight(height + "px");
    }
}
