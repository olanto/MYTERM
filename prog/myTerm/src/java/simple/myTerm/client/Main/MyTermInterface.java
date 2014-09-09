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
package simple.myTerm.client.Main;

import simple.myTerm.client.Main.publicWidget.MyTermSearchWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import simple.myTerm.client.Main.EditorWidget.EditorInterfaceWidget;
import simple.myTerm.client.Main.ValidatorWidget.ApproverInterfaceWidget;
import simple.myTerm.client.Main.cookiesManager.MyTermCookiesNamespace;

/**
 * Main entry point.
 *
 * @author nizar ghoula - simple
 */
public class MyTermInterface implements EntryPoint {

    public String UserProfile = Cookies.getCookie(MyTermCookiesNamespace.UserProfile);
    /**
     * Creates a new instance of MyTermInterface
     */
    public MyTermInterface() {
    }

    /**
     * The entry point method, called automatically by loading a module that
     * declares an implementing class as an entry-point
     */
    @Override
    public void onModuleLoad() {
        VerticalPanel vpan = null;
        if (UserProfile.contains("public")) {
            vpan = new MyTermSearchWidget();
        } else if (UserProfile.contains("inputter")) {
            vpan = new EditorInterfaceWidget();
        }else if (UserProfile.contains("approver")) {
            vpan = new ApproverInterfaceWidget();
        }
        RootLayoutPanel.get().add(vpan);
    }
}