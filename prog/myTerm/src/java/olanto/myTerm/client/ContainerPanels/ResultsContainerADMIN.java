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

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.shared.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class ResultsContainerADMIN extends HorizontalPanel {

    public ScrollPanel sideRes;
    public ScrollPanel termsDetails;
    private VerticalPanel resVP;
    private VerticalPanel sideVP;

    public ResultsContainerADMIN() {
        sideRes = new ScrollPanel();
        termsDetails = new ScrollPanel();
        resVP = new VerticalPanel();
        sideVP = new VerticalPanel();
        add(sideVP);
        add(resVP);
        resVP.add(termsDetails);
        sideVP.add(sideRes);
        sideRes.setStyleName("sideWidget");
        termsDetails.setStyleName("termsContainer");
        setStyleName("resultsContainer");
    }

    public void adjustSize() {
        int h = Window.getClientHeight() - GuiConstant.ADMIN_HEADER_HEIGHT;
        int w = Window.getClientWidth() - GuiConstant.WIDTH_UNIT;
        sideRes.setPixelSize((int) (w * 1 / 3), h);
        termsDetails.setPixelSize(w * 2 / 3, h);
    }
}
