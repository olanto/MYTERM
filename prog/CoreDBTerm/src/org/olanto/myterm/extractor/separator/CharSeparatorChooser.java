package org.olanto.myterm.extractor.separator;

import org.olanto.myterm.extractor.model.posfix.ModelPosfix;

/**
 * ********
 * Copyright � 2013-2014 Olanto Foundation Geneva
 *
 * This file is part of myTERM.
 *
 * myCAT is free software: you can redistribute it and/or modify it under the
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
 * along with myCAT. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
public class CharSeparatorChooser {

    public static String select(ModelPosfix model) {
        switch (model.getSeparator()) {
            case "TAB":
                return "\t";
            case "SEMICOLUMN":
                return ";";
            case "DIV":
                return "/";
            default:
                System.out.println("Format:" + model.getSeparator() + " not implemented ...");
                return model.getSeparator();
        }

    }
}
