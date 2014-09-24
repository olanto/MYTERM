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
package simple.myTerm.client.Main.EditorWidget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author nizar ghoula - simple
 */
public class SearchEditResultsContainer extends HorizontalPanel {

    public ScrollPanel termsPan = new ScrollPanel();
    public ScrollPanel resultsPan = new ScrollPanel();
    public VerticalPanel vp = new VerticalPanel();
    public VerticalPanel vptop = new VerticalPanel();

    public SearchEditResultsContainer() {
        add(termsPan);
        add(vp);
        vp.add(vptop);
        vp.add(resultsPan);
        termsPan.setStyleName("sideWidget");
        resultsPan.setStyleName("containerWidget");
        resultsPan.setPixelSize((Window.getClientWidth() - 40) * 3 / 5, (Window.getClientHeight() - 250));
        termsPan.setPixelSize((Window.getClientWidth() - 40) * 2 / 5, (Window.getClientHeight() - 250));
        vptop.setWidth(((Window.getClientWidth() - 40) * 3 / 5) + "px");

        setCellHorizontalAlignment(termsPan, HorizontalPanel.ALIGN_LEFT);
        setCellHorizontalAlignment(resultsPan, HorizontalPanel.ALIGN_CENTER);
    }

    public void adjustHeight(int height) {
        resultsPan.setHeight(height + "px");
        termsPan.setHeight(height + "px");
    }
}
