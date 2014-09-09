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

import simple.myTerm.client.Main.EditorWidget.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

/**
 *
 * @author nizar ghoula - simple
 */
public class ExpSearchResultsContainer extends HorizontalPanel {

    public ScrollPanel termsPan = new ScrollPanel();
    public ScrollPanel resultsPan = new ScrollPanel();
    public ExpConceptPanel res = new ExpConceptPanel();

    public ExpSearchResultsContainer() {
        add(termsPan);
        add(resultsPan);
        resultsPan.add(res);
        resultsPan.setPixelSize(Window.getClientWidth() * 3 / 4, (Window.getClientHeight() - 90));
        termsPan.setPixelSize(Window.getClientWidth() / 4, (Window.getClientHeight() - 90));
        setCellHorizontalAlignment(termsPan, HorizontalPanel.ALIGN_LEFT);
        setCellHorizontalAlignment(resultsPan, HorizontalPanel.ALIGN_CENTER);
    }
}
