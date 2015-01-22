/**
 * ********
 * Copyright © 2013-2015 Olanto Foundation Geneva
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
package olanto.myTerm.client.Forms;

import com.google.gwt.user.client.ui.TextArea;
import java.util.HashMap;
import olanto.myTerm.shared.SysFieldDTO;

/**
 *
 * @author nizar ghoula simple
 */
public class TextAreaMyTerm extends TextArea {

    public int position = 1;
    public Boolean isEdited = false;

    TextAreaMyTerm(String type, HashMap<String, SysFieldDTO> sFields) {
        super();
        this.setVisible(sFields.get(type).getVisibility());
        this.position = sFields.get(type).getPosition();
    }
    
}
