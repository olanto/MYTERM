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
package olanto.myTerm.client.PublicWidget;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author nizar ghoula - simple
 */
public class ResultsContainer extends HorizontalPanel {

    public ScrollPanel termsPan = new ScrollPanel();
    public ScrollPanel termsDetails = new ScrollPanel();
    public VerticalPanel vp = new VerticalPanel();
    public ScrollPanel conceptDetails = new ScrollPanel();

    public ResultsContainer() {
        add(termsPan);
        add(vp);
        vp.add(conceptDetails);
        vp.add(termsDetails);
        termsPan.setStyleName("sideWidget");
        vp.setStyleName("containerWidget");
        setStyleName("resContainer");
    }

    public void adjustSize() {
        int h = Window.getClientHeight() - 150;
        termsPan.setHeight(h + "px");
        termsDetails.setHeight(h * 3 / 4 + "px");
        conceptDetails.setHeight(h * 1 / 4 + "px");
    }
}
