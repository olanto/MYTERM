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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import olanto.myTerm.client.ConstantsManager.GuiConstant;

/**
 *
 * @author nizar ghoula - simple
 */
public class FooterStatusPanel extends HorizontalPanel {

    private HorizontalPanel contact = new HorizontalPanel();
    public Label msg = new Label();

    public FooterStatusPanel() {
        setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        setStyleName("headerPanel");
        add(msg);
        setCellHorizontalAlignment(msg, HorizontalPanel.ALIGN_LEFT);
        add(contact);
        setCellHorizontalAlignment(contact, HorizontalPanel.ALIGN_RIGHT);
        contact.setPixelSize(300, 25);
        contact.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
        Anchor poweredBy = new Anchor(GuiConstant.OLANTO_URL.substring(0, GuiConstant.OLANTO_URL.indexOf("|")), true,
                GuiConstant.OLANTO_URL.substring(GuiConstant.OLANTO_URL.indexOf("|") + 1, GuiConstant.OLANTO_URL.lastIndexOf("|")),
                GuiConstant.OLANTO_URL.substring(GuiConstant.OLANTO_URL.lastIndexOf("|") + 1));
        contact.add(poweredBy);
        poweredBy.setStylePrimaryName("contactInfo");
        contact.setCellHorizontalAlignment(poweredBy, HorizontalPanel.ALIGN_LEFT);
        contact.add(new HTML("||"));
        Anchor feedback = new Anchor(GuiConstant.FEEDBACK_MAIL.substring(0, GuiConstant.FEEDBACK_MAIL.indexOf("|")));
        contact.add(feedback);
        feedback.setStylePrimaryName("contactInfo");
        contact.setCellHorizontalAlignment(feedback, HorizontalPanel.ALIGN_RIGHT);
        contact.add(new HTML("&nbsp;"));
        feedback.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                mailto(GuiConstant.FEEDBACK_MAIL.substring(GuiConstant.FEEDBACK_MAIL.lastIndexOf("|") + 1), "Feedback about myTerm");
            }
        });
    }

    public void mailto(String address, String subject) {
        Window.open("mailto:" + address + "?subject=" + URL.encode(subject), "_blank", "");
    }
}
