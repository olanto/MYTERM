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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *
 * @author nizar ghoula - simple
 */
public class ResultsContainerWorkspace extends HorizontalPanel {

    public ScrollPanel sideRes = new ScrollPanel();
    public ScrollPanel sideCurrent = new ScrollPanel();
    public ScrollPanel termsDetails = new ScrollPanel();
    private VerticalPanel resVP = new VerticalPanel();
    private VerticalPanel sideVP = new VerticalPanel();
    public ScrollPanel conceptDetails = new ScrollPanel();
    public Button addnewcpt = new Button("Create New Concept");

    public ResultsContainerWorkspace() {
        add(sideVP);
        add(resVP);
        resVP.add(conceptDetails);
        resVP.add(termsDetails);
        sideVP.add(sideRes);
        sideVP.add(addnewcpt);
        sideVP.add(sideCurrent);
        sideVP.setStyleName("side");
        sideRes.setStyleName("sideWidget");
        sideCurrent.setStyleName("sideCurrent");
        conceptDetails.setStyleName("conceptContainer");
        termsDetails.setStyleName("termsContainer");
        setStyleName("resultsContainer");
        addnewcpt.setVisible(false);
    }

    public void adjustSize() {
        int h = Window.getClientHeight() - 140;
        int w = Window.getClientWidth() - 15;
        sideRes.setPixelSize((int) (w * 1/4), (h / 2 - 40));
        sideCurrent.setPixelSize((int) (w * 1/4), h / 2);
        termsDetails.setPixelSize(w * 3 / 4, h * 4 / 5);
        conceptDetails.setPixelSize(w * 3 / 4, h * 1 / 5);
        sideVP.setCellHorizontalAlignment(addnewcpt, HorizontalPanel.ALIGN_CENTER);
    }

    public void adjustSize(float s_widthper, float s_heightper) {
        int h = Window.getClientHeight() - 140;
        int w = Window.getClientWidth() - 15;
        sideRes.setPixelSize((int) (w * s_widthper), (h / 2 - 40));
        sideCurrent.setPixelSize((int) (w * s_widthper), h / 2);
        sideVP.setCellHorizontalAlignment(addnewcpt, HorizontalPanel.ALIGN_CENTER);
        termsDetails.setPixelSize((int) (w * (1 - s_widthper)), (int) (h * (1 - s_heightper)));
        conceptDetails.setPixelSize((int) (w * (1 - s_widthper)), (int) (h * s_heightper));
    }
}
