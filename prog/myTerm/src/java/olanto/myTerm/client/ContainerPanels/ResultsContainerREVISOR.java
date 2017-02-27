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
public class ResultsContainerREVISOR extends HorizontalPanel {

    public ScrollPanel sideRes;
    public ScrollPanel termsDetails;
    private VerticalPanel resVP;
    private VerticalPanel sideVP;
    public ScrollPanel conceptDetails;
    public HorizontalPanel printButtonArea;
    public Button printBtn;

    public ResultsContainerREVISOR() {
        printBtn = new Button("Print");
        printButtonArea = new HorizontalPanel();
        sideRes = new ScrollPanel();
        termsDetails = new ScrollPanel();
        resVP = new VerticalPanel();
        sideVP = new VerticalPanel();
        conceptDetails = new ScrollPanel();

        add(sideVP);
        add(resVP);
        resVP.add(printButtonArea);
        printButtonArea.add(printBtn);
        resVP.add(conceptDetails);
        resVP.add(termsDetails);
        sideVP.add(sideRes);
        sideRes.setStyleName("sideWidget");
        conceptDetails.setStyleName("conceptContainer");
        termsDetails.setStyleName("termsContainer");
        setStyleName("resultsContainer");
        printBtn.setVisible(false);
    }

    public void adjustSize() {
        int h = Window.getClientHeight() - GuiConstant.HEADER_HEIGHT;
        int w = Window.getClientWidth() - GuiConstant.WIDTH_UNIT - 2;
        sideRes.setHeight(h + "px");
        printButtonArea.setHeight(GuiConstant.HEIGHT_UNIT + "px");
        if ((w * 1 / 4) > GuiConstant.SIDE_WIDTH) {
            sideRes.setWidth(GuiConstant.SIDE_WIDTH + "px");
            conceptDetails.setWidth((w - GuiConstant.SIDE_WIDTH) + "px");
            printButtonArea.setWidth((w - GuiConstant.SIDE_WIDTH - 20) + "px");
            termsDetails.setWidth((w - GuiConstant.SIDE_WIDTH) + "px");
        } else {
            sideRes.setWidth((int) (w * 1 / 4) + "px");
            conceptDetails.setWidth((int) (w * 3 / 4) + "px");
            termsDetails.setWidth((int) (w * 3 / 4) + "px");
            printButtonArea.setWidth((int) (w * 3 / 4) - 20 + "px");
        }
        if ((h * 1 / 6) > GuiConstant.CPT_HEIGHT) {
            conceptDetails.setHeight(GuiConstant.CPT_HEIGHT + "px");
            termsDetails.setHeight((h - GuiConstant.CPT_HEIGHT) + "px");
        } else {
            conceptDetails.setHeight((int) (h * 1 / 6) + "px");
            termsDetails.setHeight((int) (h * 5 / 6) + "px");
        }
        printButtonArea.setCellHorizontalAlignment(printBtn, HorizontalPanel.ALIGN_RIGHT);
    }

    public void adjustSize(float s_widthper, float s_heightper) {
        int h = Window.getClientHeight() - GuiConstant.HEADER_HEIGHT;
        int w = Window.getClientWidth() - GuiConstant.WIDTH_UNIT - 2;
        sideRes.setHeight(h + "px");
        printButtonArea.setHeight(GuiConstant.HEIGHT_UNIT + "px");
        if ((int) (w * s_widthper) > GuiConstant.SIDE_WIDTH) {
            sideRes.setWidth(GuiConstant.SIDE_WIDTH + "px");
            conceptDetails.setWidth((w - GuiConstant.SIDE_WIDTH) + "px");
            printButtonArea.setWidth((w - GuiConstant.SIDE_WIDTH - 20) + "px");
            termsDetails.setWidth((w - GuiConstant.SIDE_WIDTH) + "px");
        } else {
            sideRes.setWidth((int) (w * s_widthper) + "px");
            conceptDetails.setWidth((int) (w * (1 - s_widthper)) + "px");
            printButtonArea.setWidth((int) (w * (1 - s_widthper) - 20) + "px");
            termsDetails.setWidth((int) (w * (1 - s_widthper)) + "px");
        }
        if ((int) (h * s_heightper) > GuiConstant.CPT_HEIGHT) {
            conceptDetails.setHeight(GuiConstant.CPT_HEIGHT + "px");
            termsDetails.setHeight((h - GuiConstant.CPT_HEIGHT) + "px");
        } else {
            conceptDetails.setHeight((int) (h * s_heightper) + "px");
            termsDetails.setHeight((int) (h * (1 - s_heightper)) + "px");
        }
        printButtonArea.setCellHorizontalAlignment(printBtn, HorizontalPanel.ALIGN_RIGHT);
    }
}
