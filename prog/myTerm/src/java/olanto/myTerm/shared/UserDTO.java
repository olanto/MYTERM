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
package olanto.myTerm.shared;

/**
 *
 * @author nizar ghoula - simple
 */
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDTO implements IsSerializable {

    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String sessionID;

    public String getSessionID() {
        return sessionID;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setFirstName(String fname) {
        this.firstname = fname;
    }

    public void setLastName(String lname) {
        this.lastname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
}