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
package simple.myTerm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;

/**
 *
 * @author nizar ghoula - simple
 */
public interface myTermServiceAsync {

    public void getSearchResult(String s, String ls, String lt, AsyncCallback<String> callback);
    public void getResults(String s, String ls, String lt, AsyncCallback<ArrayList<String>> callback);
    public void getInventory(AsyncCallback<String> callback);
    public void getLanguages(AsyncCallback<ArrayList<String>> asyncCallback);
}
