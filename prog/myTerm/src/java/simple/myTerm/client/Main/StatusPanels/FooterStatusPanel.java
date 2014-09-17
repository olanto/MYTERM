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
package simple.myTerm.client.Main.StatusPanels;

import com.google.gwt.user.client.Window;
import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_JUSTIFY;
import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 *
 * @author nizar ghoula - simple
 */
public class FooterStatusPanel extends HorizontalPanel {

    public FooterStatusPanel() {
        setPixelSize(Window.getClientWidth(), 30);
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_JUSTIFY);        
        setStyleName("headerPanel");
    }
}
