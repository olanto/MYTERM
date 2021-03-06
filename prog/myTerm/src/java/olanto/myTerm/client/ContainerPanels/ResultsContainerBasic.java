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
import olanto.myTerm.shared.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class ResultsContainerBasic extends HorizontalPanel {

    public ScrollPanel sideRes;
    public ScrollPanel termsDetails;
    public VerticalPanel resVP;
    public ScrollPanel conceptDetails;
    public HorizontalPanel printButtonArea;
    public Button printBtn;

    public ResultsContainerBasic() {
        printBtn = new Button("Print");
        printButtonArea = new HorizontalPanel();
        sideRes = new ScrollPanel();
        termsDetails = new ScrollPanel();
        resVP = new VerticalPanel();
        conceptDetails = new ScrollPanel();
        add(sideRes);
        add(resVP);
        resVP.add(printButtonArea);
        printButtonArea.add(printBtn);
        resVP.add(conceptDetails);
        resVP.add(termsDetails);
        sideRes.setStyleName("sideWidget");
        conceptDetails.setStyleName("conceptContainer");
        termsDetails.setStyleName("termsContainer");
        setStyleName("resultsContainer");
        printBtn.setVisible(false);
    }

    public void adjustSize() {
        int h = Window.getClientHeight() - GuiConstant.HEADER_HEIGHT;
        int w = Window.getClientWidth() - GuiConstant.WIDTH_UNIT;
        sideRes.setPixelSize(w * 1 / 4, h);
        termsDetails.setPixelSize(w * 3 / 4, h * 5 / 6);
        conceptDetails.setPixelSize(w * 3 / 4, h * 1 / 6);
        printButtonArea.setPixelSize(w * 3 / 4 - 20, GuiConstant.HEIGHT_UNIT);
        printButtonArea.setCellHorizontalAlignment(printBtn, HorizontalPanel.ALIGN_RIGHT);
    }

    public void adjustSize(float s_widthper, float s_heightper) {
        int h = Window.getClientHeight() - GuiConstant.HEADER_HEIGHT;
        int w = Window.getClientWidth() - GuiConstant.WIDTH_UNIT;
        sideRes.setPixelSize((int) (w * s_widthper), h);
        termsDetails.setPixelSize((int) (w * (1 - s_widthper)), (int) (h * (1 - s_heightper)));
        conceptDetails.setPixelSize((int) (w * (1 - s_widthper)), (int) (h * s_heightper));
        printButtonArea.setPixelSize((int) (w * (1 - s_widthper) - 20), GuiConstant.HEIGHT_UNIT);
        printButtonArea.setCellHorizontalAlignment(printBtn, HorizontalPanel.ALIGN_RIGHT);
    }
}
