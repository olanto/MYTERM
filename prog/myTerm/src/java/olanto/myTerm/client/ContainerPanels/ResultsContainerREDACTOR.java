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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.HashMap;
import olanto.myTerm.shared.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class ResultsContainerREDACTOR extends HorizontalPanel {
    
    public ScrollPanel sideRes;
    public ScrollPanel sideCurrent;
    public ScrollPanel termsDetails;
    private VerticalPanel resVP;
    private VerticalPanel sideVP;
    public ScrollPanel conceptDetails;
    public Button addnewcpt;
    public HorizontalPanel buttonsPanel;
    private Label currentHeader;
    public HorizontalPanel printButtonArea;
    public Button printBtn;
    
    public ResultsContainerREDACTOR(HashMap<String, String> sysMsg) {
        printBtn = new Button("Print");
        printButtonArea = new HorizontalPanel();
        sideRes = new ScrollPanel();
        sideCurrent = new ScrollPanel();
        termsDetails = new ScrollPanel();
        resVP = new VerticalPanel();
        sideVP = new VerticalPanel();
        conceptDetails = new ScrollPanel();
        addnewcpt = new Button(sysMsg.get(GuiConstant.BTN_CREATE_NEW));
        buttonsPanel = new HorizontalPanel();
        currentHeader = new Label(sysMsg.get(GuiConstant.MSG_WORKSPACE_ENTRIES));
        add(sideVP);
        add(resVP);
        resVP.add(printButtonArea);
        printButtonArea.add(printBtn);
        resVP.add(conceptDetails);
        resVP.add(termsDetails);
        sideVP.add(sideRes);
        sideVP.add(buttonsPanel);
        sideVP.setStyleName("sideWidget");
        buttonsPanel.add(addnewcpt);
        sideVP.add(currentHeader);
        currentHeader.setStyleName("sidecurhead");
        sideVP.add(sideCurrent);
        conceptDetails.setStyleName("conceptContainer");
        termsDetails.setStyleName("termsContainer");
        setStyleName("resultsContainer");
        addnewcpt.setVisible(false);
        printBtn.setVisible(false);
        addnewcpt.setStyleName("addcpt");
    }
    
    public void adjustSize() {
        int h = Window.getClientHeight() - GuiConstant.HEADER_HEIGHT_EXTRA;
        int w = Window.getClientWidth() - GuiConstant.WIDTH_UNIT_EXTRA;
        sideRes.setPixelSize((int) (w * 1 / 4) - 3, (h / 2 - 30));
        buttonsPanel.setPixelSize((int) (w * 1 / 4) + 2, 30);
        sideCurrent.setPixelSize((int) (w * 1 / 4), h / 2);
        termsDetails.setPixelSize(w * 3 / 4, h * 5 / 6);
        conceptDetails.setPixelSize(w * 3 / 4, h * 1 / 6);
        printButtonArea.setPixelSize(w * 3 / 4 - 20, GuiConstant.HEIGHT_UNIT);
        currentHeader.setWidth((w * 1 / 4) - 3 + "px");
        sideVP.setWidth((w * 1 / 4) + "px");
        buttonsPanel.setCellHorizontalAlignment(addnewcpt, HorizontalPanel.ALIGN_RIGHT);
        printButtonArea.setPixelSize(w * 3 / 4, h * 1 / 6);
        printButtonArea.setCellHorizontalAlignment(printBtn, HorizontalPanel.ALIGN_RIGHT);
    }
    
    public void adjustSize(float s_widthper, float s_heightper) {
        int h = Window.getClientHeight() - GuiConstant.HEADER_HEIGHT_EXTRA;
        int w = Window.getClientWidth() - GuiConstant.WIDTH_UNIT_EXTRA;
        sideVP.setWidth((int) (w * s_widthper) + "px");
        sideRes.setHeight((int) (h / 2 - 30) + "px");
        buttonsPanel.setPixelSize((int) (w * s_widthper), 30);
        sideCurrent.setPixelSize((int) (w * s_widthper), h / 2);
        currentHeader.setPixelSize((int) (w * s_widthper), GuiConstant.WIDTH_UNIT_EXTRA);
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
        printButtonArea.setHeight(GuiConstant.HEIGHT_UNIT + "px");
        buttonsPanel.setCellHorizontalAlignment(addnewcpt, HorizontalPanel.ALIGN_RIGHT);
        printButtonArea.setCellHorizontalAlignment(printBtn, HorizontalPanel.ALIGN_RIGHT);
    }
}
