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
        conceptDetails.setStyleName("conceptContainer");
        termsDetails.setStyleName("termsContainer");
        setStyleName("resultsContainer");
    }

    public void adjustSize() {
        int h = Window.getClientHeight() - 140;
        int w = Window.getClientWidth() - 15;
        termsPan.setPixelSize(w * 1 / 4, h);
        termsDetails.setPixelSize(w * 3 / 4, h * 4 / 5);
        conceptDetails.setPixelSize(w * 3 / 4, h * 1 / 5);
    }

    public void adjustSize(float s_widthper, float s_heightper) {
        int h = Window.getClientHeight() - 140;
        int w = Window.getClientWidth() - 15;
        termsPan.setPixelSize((int) (w * s_widthper), h);
        termsDetails.setPixelSize((int) (w * (1 - s_widthper)), (int) (h * (1 - s_heightper)));
        conceptDetails.setPixelSize((int) (w * (1 - s_widthper)), (int) (h * s_heightper));
    }
}
